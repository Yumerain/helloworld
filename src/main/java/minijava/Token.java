package minijava;

/**
 * 标记符
 * @author zhangyu
 */
public class Token {

	public Type type;
	public Object value;
	
	public Token(Type type, Object value) {
		this.type = type;
		this.value = value;
	}
	
	public String toString() {
		return String.format("Token(%s, %s)", type, value);
	}
	
	/**
	 * 类别
	 */
	enum Type {
		INTEGER,				// 整数 
		OP_PLUS, 				// 加法
		OP_MINUS,				// 减法
		OP_MULTIPLY,		// 乘法
		OP_DIVISION,			// 除法
		EOF							// 数据结束
	}
}
