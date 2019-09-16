package myjava.ast;

import myjava.Location;
import myjava.Scope;

/**
 * Expr
 */
public abstract class Expr {
	
	protected Location location;
	
	public abstract Object eval(Scope scope);
}




