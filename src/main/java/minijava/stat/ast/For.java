package minijava.stat.ast;


import java.util.Iterator;

import minijava.Ctrl;
import minijava.Env;
import minijava.expr.ast.Expr;
import minijava.expr.ast.ForCtrl;
import minijava.expr.ast.Logic;
import minijava.io.Writer;
import minijava.stat.Scope;

/**
 * For 循环控制，支持 List、Map、数组、Collection、Iterator、Iterable
 * Enumeration、null 以及任意单个对象的迭代，简单说是支持所有对象迭代
 * 
 * 主要用法：
 * 1：#for(item : list) #(item) #end
 * 2：#for(item : list) #(item) #else content #end
 * 3：#for(i=0; i<9; i++) #(item) #end
 * 4：#for(i=0; i<9; i++) #(item) #else content #end
 */
public class For extends Stat {
	
	private ForCtrl forCtrl;
	private Stat stat;
	private Stat _else;
	
	public For(ForCtrl forCtrl, StatList statList, Stat _else) {
		this.forCtrl = forCtrl;
		this.stat = statList.getActualStat();
		this._else = _else;
	}
	
	public void exec(Env env, Scope scope, Writer writer) {
		scope = new Scope(scope);
		if (forCtrl.isIterator()) {
			forIterator(env, scope, writer);
		} else {
			forLoop(env, scope, writer);
		}
	}
	
	/**
	 * #for( id : expr)
	 */
	private void forIterator(Env env, Scope scope, Writer writer) {
		Ctrl ctrl = scope.getCtrl();
		Object outer = scope.get("for");
		ctrl.setLocalAssignment();
		ForIteratorStatus forIteratorStatus = new ForIteratorStatus(outer, forCtrl.getExpr().eval(scope), location);
		ctrl.setWisdomAssignment();
		scope.setLocal("for", forIteratorStatus);
		
		Iterator<?> it = forIteratorStatus.getIterator();
		String itemName = forCtrl.getId();
		while(it.hasNext()) {
			scope.setLocal(itemName, it.next());
			stat.exec(env, scope, writer);
			forIteratorStatus.nextState();
			
			if (ctrl.isJump()) {
				if (ctrl.isBreak()) {
					ctrl.setJumpNone();
					break ;
				} else if (ctrl.isContinue()) {
					ctrl.setJumpNone();
					continue ;
				} else {
					return ;
				}
			}
		}
		
		if (_else != null && forIteratorStatus.getIndex() == 0) {
			_else.exec(env, scope, writer);
		}
	}
	
	/**
	 * #for(exprList; cond; update)
	 */
	private void forLoop(Env env, Scope scope, Writer writer) {
		Ctrl ctrl = scope.getCtrl();
		Object outer = scope.get("for");
		ForLoopStatus forLoopStatus = new ForLoopStatus(outer);
		scope.setLocal("for", forLoopStatus);
		
		Expr init = forCtrl.getInit();
		Expr cond = forCtrl.getCond();
		Expr update = forCtrl.getUpdate();
		
		ctrl.setLocalAssignment();
		for (init.eval(scope); cond == null || Logic.isTrue(cond.eval(scope)); update.eval(scope)) {
			ctrl.setWisdomAssignment();
			stat.exec(env, scope, writer);
			ctrl.setLocalAssignment();
			forLoopStatus.nextState();
			
			if (ctrl.isJump()) {
				if (ctrl.isBreak()) {
					ctrl.setJumpNone();
					break ;
				} else if (ctrl.isContinue()) {
					ctrl.setJumpNone();
					continue ;
				} else {
					ctrl.setWisdomAssignment();
					return ;
				}
			}
		}
		
		ctrl.setWisdomAssignment();
		if (_else != null && forLoopStatus.getIndex() == 0) {
			_else.exec(env, scope, writer);
		}
	}
}






