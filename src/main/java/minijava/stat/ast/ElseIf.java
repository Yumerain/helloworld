package minijava.stat.ast;

import minijava.Env;
import minijava.expr.ast.Expr;
import minijava.expr.ast.ExprList;
import minijava.expr.ast.Logic;
import minijava.io.Writer;
import minijava.stat.Location;
import minijava.stat.ParseException;
import minijava.stat.Scope;

/**
 * ElseIf
 */
public class ElseIf extends Stat {
	
	private Expr cond;
	private Stat stat;
	private Stat elseIfOrElse;
	
	public ElseIf(ExprList cond, StatList statList, Location location) {
		if (cond.length() == 0) {
			throw new ParseException("The condition expression of #else if statement can not be blank", location);
		}
		this.cond = cond.getActualExpr();
		this.stat = statList.getActualStat();
	}
	
	/**
	 * take over setStat(...) method of super class
	 */
	public void setStat(Stat elseIfOrElse) {
		this.elseIfOrElse = elseIfOrElse;
	}
	
	public void exec(Env env, Scope scope, Writer writer) {
		if (Logic.isTrue(cond.eval(scope))) {
			stat.exec(env, scope, writer);
		} else if (elseIfOrElse != null) {
			elseIfOrElse.exec(env, scope, writer);
		}
	}
}





