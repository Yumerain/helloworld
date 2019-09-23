package minijava.stat.ast;

import minijava.Env;
import minijava.io.Writer;
import minijava.stat.Scope;

/**
 * Else
 */
public class Else extends Stat {
	
	private Stat stat;
	
	public Else(StatList statList) {
		this.stat = statList.getActualStat();
	}
	
	public void exec(Env env, Scope scope, Writer writer) {
		stat.exec(env, scope, writer);
	}
}



