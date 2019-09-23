package minijava.stat.ast;

import minijava.Env;
import minijava.TemplateException;
import minijava.expr.ast.Expr;
import minijava.expr.ast.ExprList;
import minijava.io.Writer;
import minijava.stat.Location;
import minijava.stat.ParseException;
import minijava.stat.Scope;

/**
 * Output 输出指令
 * 
 * 用法：
 * 1：#(value)
 * 2：#(x = 1, y = 2, x + y)
 * 3：#(seoTitle ?? 'JFinal 极速开发社区')
 */
public class Output extends Stat {
	
	private Expr expr;
	
	public Output(ExprList exprList, Location location) {
		if (exprList.length() == 0) {
			throw new ParseException("The expression of output directive like #(expression) can not be blank", location);
		}
		this.expr = exprList.getActualExpr();
	}
	
	public void exec(Env env, Scope scope, Writer writer) {
		try {
			Object value = expr.eval(scope);
			
			if (value instanceof String) {
				String str = (String)value;
				writer.write(str, 0, str.length());
			} else if (value instanceof Number) {
				Class<?> c = value.getClass();
				if (c == Integer.class) {
					writer.write((Integer)value);
				} else if (c == Long.class) {
					writer.write((Long)value);
				} else if (c == Double.class) {
					writer.write((Double)value);
				} else if (c == Float.class) {
					writer.write((Float)value);
				} else if (c == Short.class) {
					writer.write((Short)value);
				} else {
					writer.write(value.toString());
				}
			} else if (value instanceof Boolean) {
				writer.write((Boolean)value);
			} else if (value != null) {
				writer.write(value.toString());
			}
		} catch(TemplateException | ParseException e) {
			throw e;
		} catch(Exception e) {
			throw new TemplateException(e.getMessage(), location, e);
		}
	}
}




