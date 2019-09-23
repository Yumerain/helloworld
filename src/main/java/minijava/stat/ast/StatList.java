package minijava.stat.ast;

import java.util.List;

import minijava.Ctrl;
import minijava.Env;
import minijava.TemplateException;
import minijava.io.Writer;
import minijava.stat.Scope;

/**
 * StatList
 */
public class StatList extends Stat {
	
	public static final Stat NULL_STAT = NullStat.me;
	public static final Stat[] NULL_STAT_ARRAY = new Stat[0];
	
	private Stat[] statArray;
	
	public StatList(List<Stat> statList) {
		if (statList.size() > 0) {
			this.statArray = statList.toArray(new Stat[statList.size()]);
		} else {
			this.statArray = NULL_STAT_ARRAY;
		}
	}
	
	/**
	 * 持有 StatList 的指令可以通过此方法提升 AST 执行性能
	 * 1：当 statArray.length >  1 时返回 StatList 自身
	 * 2：当 statArray.length == 1 时返回 statArray[0]
	 * 3：其它情况返回 NullStat
	 * 
	 * 意义在于，当满足前面两个条件时，避免掉了 StatList.exec(...) 方法中的判断与循环
	 */
	public Stat getActualStat() {
		if (statArray.length > 1) {
			return this;
		} else if (statArray.length == 1) {
			return statArray[0];
		} else {
			return NULL_STAT;
		}
	}
	
	public void exec(Env env, Scope scope, Writer writer) {
		Ctrl ctrl = scope.getCtrl();
		for (int i=0; i<statArray.length; i++) {
			if (ctrl.isJump()) {
				break ;
			}
			statArray[i].exec(env, scope, writer);	
		}
	}
	
	public int length() {
		return statArray.length;
	}
	
	public Stat getStat(int index) {
		if (index < 0 || index >= statArray.length) {
			throw new TemplateException("Index out of bounds: index = " + index + ", length = " + statArray.length, location);
		}
		return statArray[index];
	}
}


