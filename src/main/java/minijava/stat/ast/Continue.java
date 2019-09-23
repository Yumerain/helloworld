package minijava.stat.ast;

import minijava.Env;
import minijava.io.Writer;
import minijava.stat.Scope;

/**
 * Continue
 */
public class Continue extends Stat {
	
	public static final Continue me = new Continue();
	
	private Continue() {
	}
	
	public void exec(Env env, Scope scope, Writer writer) {
		scope.getCtrl().setContinue();
	}
}




