package minijava;

import java.util.ArrayList;
import java.util.List;

import minijava.expr.ast.ExprList;
import minijava.expr.ast.ForCtrl;
import minijava.stat.Location;
import minijava.stat.ParseException;
import minijava.stat.ast.Break;
import minijava.stat.ast.Continue;
import minijava.stat.ast.Define;
import minijava.stat.ast.Else;
import minijava.stat.ast.ElseIf;
import minijava.stat.ast.For;
import minijava.stat.ast.If;
import minijava.stat.ast.Return;
import minijava.stat.ast.Set;
import minijava.stat.ast.Stat;
import minijava.stat.ast.StatList;
import minijava.stat.ast.Text;

/**
 * DLRD (Double Layer Recursive Descent) Parser
 */
public class Parser {
	
	private static final Token EOF = new Token(Symbol.EOF, -1);
	
	private int forward = 0;
	private List<Token> tokenList;
	private StringBuilder content;
	private String fileName;
	private Env env;
	
	public Parser(Env env, StringBuilder content, String fileName) {
		this.env = env;
		this.content = content;
		this.fileName = fileName;
	}
	
	private Token peek() {
		return tokenList.get(forward);
	}
	
	private Token move() {
		return tokenList.get(++forward);
	}
	
	private Token matchPara(Token name) {
		Token current = peek();
		if (current.symbol == Symbol.PARA) {
			move();
			return current;
		}
		throw new ParseException("Can not match the parameter of directive #" + name.value(), getLocation(name.row));
	}
	
	private void matchEnd(Token name) {
		if (peek().symbol == Symbol.END) {
			move();
			return ;
		}
		throw new ParseException("Can not match the #end of directive #" + name.value(), getLocation(name.row));
	}
	
	public StatList parse() {
		tokenList = new Lexer(content, fileName).scan();
		tokenList.add(EOF);
		StatList statList = statList();
		if (peek() != EOF) {
			throw new ParseException("Syntax error: can not match \"#" + peek().value() + "\"", getLocation(peek().row));
		}
		return statList;
	}
	
	private StatList statList() {
		List<Stat> statList = new ArrayList<Stat>();
		while (true) {
			Stat stat = stat();
			if (stat == null) {
				break ;
			}
			
			if (stat instanceof Define) {
				env.addFunction((Define)stat);
				continue ;
			}
			
			// 过滤内容为空的 Text 节点，通常是处于两个指令之间的空白字符被移除以后的结果，详见 TextToken.deleteBlankTails()
			if (stat instanceof Text && ((Text)stat).isEmpty()) {
				continue ;
			}
			
			statList.add(stat);
		}
		return new StatList(statList);
	}
	
	private Stat stat() {
		Token name = peek();
		switch (name.symbol) {
		case TEXT:
			move();
			return new Text(((TextToken)name).getContent(), env.getEngineConfig().getEncoding()).setLocation(getLocation(name.row));
		case OUTPUT:
			move();
			Token para = matchPara(name);
			Location loc = getLocation(name.row);
			return env.getEngineConfig().getOutputDirective(parseExprList(para), loc).setLocation(loc);
//		case INCLUDE:
//			move();
//			para = matchPara(name);
//			return new Include(env, parseExprList(para), fileName, getLocation(name.row));
		case FOR:
			move();
			para = matchPara(name);//TODO Token
			StatList statList = statList();
			Stat _else = null;
			if (peek().symbol == Symbol.ELSE) {
				move();
				StatList elseStats = statList();
				_else = new Else(elseStats);
			}
			matchEnd(name);
			return new For(parseForCtrl(para), statList, _else).setLocation(getLocation(name.row));
		case IF:
			move();
			para = matchPara(name);
			statList = statList();
			Stat ret = new If(parseExprList(para), statList, getLocation(name.row));
			
			Stat current = ret;
			for (Token elseIfToken=peek(); elseIfToken.symbol == Symbol.ELSEIF; elseIfToken=peek()) {
				move();
				para = matchPara(elseIfToken);
				statList = statList();
				Stat elseIf = new ElseIf(parseExprList(para), statList, getLocation(elseIfToken.row));
				current.setStat(elseIf);
				current = elseIf;
			}
			if (peek().symbol == Symbol.ELSE) {
				move();
				statList = statList();
				_else = new Else(statList);
				current.setStat(_else);
			}
			matchEnd(name);
			return ret;
		case DEFINE:
			String functionName = name.value();
			move();
			para = matchPara(name);
			statList = statList();
			matchEnd(name);
			return new Define(functionName, parseExprList(para), statList, getLocation(name.row));
//		case CALL:
//			functionName = name.value();
//			move();
//			para = matchPara(name);
//			return new Call(functionName, parseExprList(para), false).setLocation(getLocation(name.row));
//		case CALL_IF_DEFINED:
//			functionName = name.value();
//			move();
//			para = matchPara(name);
//			return new Call(functionName, parseExprList(para), true).setLocation(getLocation(name.row));
		case SET:
			move();
			para = matchPara(name);
			return new Set(parseExprList(para), getLocation(name.row));
//		case SET_LOCAL:
//			move();
//			para = matchPara(name);
//			return new SetLocal(parseExprList(para), getLocation(name.row));
//		case SET_GLOBAL:
//			move();
//			para = matchPara(name);
//			return new SetGlobal(parseExprList(para), getLocation(name.row));
		case CONTINUE:
			move();
			return Continue.me;
		case BREAK:
			move();
			return Break.me;
		case RETURN:
			move();
			return Return.me;
		case ID:
			Class<? extends Directive> dire = env.getEngineConfig().getDirective(name.value());
			if (dire == null) {
				throw new ParseException("Directive not found: #" + name.value(), getLocation(name.row));
			}
			ret = createDirective(dire, name).setLocation(getLocation(name.row));
			move();
			para = matchPara(name);
			ret.setExprList(parseExprList(para));
			
			if (ret.hasEnd()) {
				statList = statList();
				ret.setStat(statList.getActualStat());
				matchEnd(name);
			}
			return ret;
		case PARA:
		case ELSEIF:
		case ELSE:
		case END:
		case EOF:
		case CASE:
		case DEFAULT:
			return null;
		case SWITCH:
		default :
			throw new ParseException("Syntax error: can not match the token: " + name.value(), getLocation(name.row));
		}
	}
	
	private Location getLocation(int row) {
		return new Location(fileName, row);
	}
	
	private Stat createDirective(Class<? extends Directive> dire, Token name) {
		try {
			return dire.newInstance();
		} catch (Exception e) {
			throw new ParseException(e.getMessage(), getLocation(name.row), e);
		}
	}
	
	private ExprList parseExprList(Token paraToken) {
		//return new ExprParser((ParaToken)paraToken, env.getEngineConfig(), fileName).parseExprList();
		return null;
	}
	
	private ForCtrl parseForCtrl(Token paraToken) {
		//return new ExprParser((ParaToken)paraToken, env.getEngineConfig(), fileName).parseForCtrl();
		return null;
	}
}




