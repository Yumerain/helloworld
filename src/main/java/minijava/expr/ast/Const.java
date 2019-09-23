package minijava.expr.ast;

import minijava.expr.Sym;
import minijava.stat.Scope;

/**
 * STR INT LONG FLOAT DOUBLE TRUE FALSE NULL
 */
public class Const extends Expr {
	
	public static final Const TRUE = new Const(Sym.TRUE, Boolean.TRUE);
	public static final Const FALSE = new Const(Sym.FALSE, Boolean.FALSE);
	public static final Const NULL = new Const(Sym.NULL, null);
	
	private final Sym type;
	private final Object value;
	
	/**
	 * INT LONG FLOAT DOUBLE 常量已在 NumTok 中转换成了确切的类型，无需再次转换
	 */
	public Const(Sym type, Object value) {
		this.type = type;
		this.value = value;
	}
	
	public Object eval(Scope scope) {
		return value;
	}
	
	public boolean isStr() {
		return type == Sym.STR;
	}
	
	public boolean isTrue() {
		return type == Sym.TRUE;
	}
	
	public boolean isFalse() {
		return type == Sym.FALSE;
	}
	
	public boolean isBoolean() {
		return type == Sym.TRUE || type == Sym.FALSE;
	}
	
	public boolean isNull() {
		return type == Sym.NULL;
	}
	
	public boolean isInt() {
		return type == Sym.INT;
	}
	
	public boolean isLong() {
		return type == Sym.LONG;
	}
	
	public boolean isFloat() {
		return type == Sym.FLOAT;
	}
	
	public boolean isDouble() {
		return type == Sym.DOUBLE;
	}
	
	public boolean isNumber() {
		return value instanceof Number;
	}
	
	public Object getValue() {
		return value;
	}
	
	public String getStr() {
		return (String)value;
	}
	
	public Boolean getBoolean() {
		return (Boolean)value;
	}
	
	public Integer getInt() {
		return (Integer)value;
	}
	
	public Long getLong() {
		return (Long)value;
	}
	
	public Float getFloat() {
		return (Float)value;
	}
	
	public Double getDouble() {
		return (Double)value;
	}
	
	public Number getNumber() {
		return (Number)value;
	}
	
	public String toString() {
		return value != null ? value.toString() : "null";
	}
}








