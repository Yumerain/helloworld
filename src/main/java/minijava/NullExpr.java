
package minijava;

import minijava.expr.ast.Expr;
import minijava.stat.Scope;

/**
 * NullExpr
 */
public class NullExpr extends Expr {
	
	public static final NullExpr me = new NullExpr();
	
	private NullExpr() {}
	
	public Object eval(Scope scope) {
		return null;
	}
}



