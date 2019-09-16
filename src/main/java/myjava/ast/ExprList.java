package myjava.ast;


import java.util.ArrayList;
import java.util.List;

import myjava.NullExpr;
import myjava.Scope;
import myjava.TemplateException;

/**
 * ExprList
 */
public class ExprList extends Expr {
	
	public static final Expr NULL_EXPR = NullExpr.me;
	public static final Expr[] NULL_EXPR_ARRAY = new Expr[0];
	public static final ExprList NULL_EXPR_LIST = new ExprList(new ArrayList<Expr>(0));
	
	public static final Object[] NULL_OBJECT_ARRAY =  new Object[0];
	
	private Expr[] exprArray;
	
	public ExprList(List<Expr> exprList) {
		if (exprList.size() > 0) {
			exprArray = exprList.toArray(new Expr[exprList.size()]);
		} else {
			exprArray = NULL_EXPR_ARRAY;
		}
	}
	
	/**
	 * 持有 ExprList 的指令可以通过此方法提升 AST 执行性能
	 * 1：当 exprArray.length == 1 时返回 exprArray[0]
	 * 2：当 exprArray.length == 0 时返回 NullExpr
	 * 3：其它情况返回 ExprList 自身
	 * 
	 * 意义在于，当满足前面两个条件时，避免掉了 ExprList.eval(...) 方法中的判断与循环
	 */
	public Expr getActualExpr() {
		if (exprArray.length == 1) {
			return exprArray[0];
		} else if (exprArray.length == 0) {
			return NULL_EXPR;
		} else {
			return this;
		}
	}
	
	public Expr[] getExprArray() {
		return exprArray;
	}
	
	public Expr getExpr(int index) {
		if (index < 0 || index >= exprArray.length) {
			throw new TemplateException("Index out of bounds: index = " + index + ", length = " + exprArray.length, location);
		}
		return exprArray[index];
	}
	
	public Expr getFirstExpr() {
		return exprArray.length > 0 ? exprArray[0] : null;
	}
	
	public Expr getLastExpr() {
		return exprArray.length > 0 ? exprArray[exprArray.length - 1] : null;
	}
	
	public int length() {
		return exprArray.length;
	}
	
	/**
	 * 对所有表达式求值，只返回最后一个表达式的值
	 */
	public Object eval(Scope scope) {
		// 优化：绝大多数情况下 length 等于 1
		if (exprArray.length == 1) {
			return exprArray[0].eval(scope);
		}
		
		if (exprArray.length == 0) {
			return null;
		}
		
		int end = exprArray.length - 1;
		for (int i=0; i<end; i++) {
			exprArray[i].eval(scope);
		}
		return exprArray[end].eval(scope);
	}
	
	/**
	 * 对所有表达式求值，并返回所有表达式的值
	 */
	public Object[] evalExprList(Scope scope) {
		if (exprArray.length == 0) {
			return NULL_OBJECT_ARRAY;
		}
		
		Object[] ret = new Object[exprArray.length];
		for (int i=0; i<exprArray.length; i++) {
			ret[i] = exprArray[i].eval(scope);
		}
		return ret;
	}
}




