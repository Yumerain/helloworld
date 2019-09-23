package minijava.stat.ast;

import minijava.Env;
import minijava.io.Writer;
import minijava.stat.Scope;

/**
 * NullStat
 */
public class NullStat extends Stat {
	
	public static final NullStat me = new NullStat();
	
	private NullStat() {}
	
	public void exec(Env env, Scope scope, Writer writer) {
		
	}
}





