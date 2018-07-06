package myjava;

/**
 * 标记符
 * @author zhangyu
 */
public class Token {

	public Type tokenType;
	public Object value;
	
	public Token(Type tokenType, Object value) {
		this.tokenType = tokenType;
		this.value = value;
	}
	
	public String toString() {
		return String.format("Token(%s, %s)", tokenType, value);
	}
	
	/**
	 * 类别
	 */
	enum Type {
		INTEGER,					// 整数 
		OP_PLUS, 					// 加法
		OP_MINUS,					// 减法
		OP_MULTIPLY,			// 乘法
		OP_DIVISION,				// 除法
		EOF							// 数据结束
	}
}
