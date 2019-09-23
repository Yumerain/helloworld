package minijava.stat.ast;

import minijava.Env;
import minijava.io.Writer;
import minijava.stat.Scope;

/**
 * Return
 * 通常用于 #define 指令内部，不支持返回值
 */
public class Return  extends Stat {
	
	public static final Return me = new Return();
	
	private Return() {
	}
	
	public void exec(Env env, Scope scope, Writer writer) {
		scope.getCtrl().setReturn();
	}
}







