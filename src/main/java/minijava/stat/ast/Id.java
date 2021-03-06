package minijava.stat.ast;

import minijava.expr.ast.Expr;
import minijava.stat.Scope;

/**
 * Id
 */
public class Id extends Expr {
	
	private final String id;
	
	public Id(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
	public Object eval(Scope scope) {
		return scope.get(id);
	}
	
	/**
	 * Id.toString() 后续版本不能变动，已有部分第三方依赖此方法
	 */
	public String toString() {
		return id;
	}
}


