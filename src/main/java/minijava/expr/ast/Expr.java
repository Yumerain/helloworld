package minijava.expr.ast;

import minijava.stat.Location;
import minijava.stat.Scope;

/**
 * Expr
 */
public abstract class Expr {
	
	protected Location location;
	
	public abstract Object eval(Scope scope);
}




