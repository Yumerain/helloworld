
package myjava;

import myjava.ast.Expr;

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



