package minijava.ast;

import minijava.Env;
import minijava.Scope;
import minijava.Writer;

/**
 * NullStat
 */
public class NullStat extends Stat {
	
	public static final NullStat me = new NullStat();
	
	private NullStat() {}
	
	public void exec(Env env, Scope scope, Writer writer) {
		
	}
}





