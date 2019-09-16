package myjava.ast;

import myjava.Env;
import myjava.Scope;
import myjava.Writer;

/**
 * NullStat
 */
public class NullStat extends Stat {
	
	public static final NullStat me = new NullStat();
	
	private NullStat() {}
	
	public void exec(Env env, Scope scope, Writer writer) {
		
	}
}





