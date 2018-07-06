package myjava;

public class Interpreter {
	
	protected Lexer lexer;
	
	protected Token currToken;
	
	public Interpreter(Lexer lexer){
		this.lexer = lexer;
		this.currToken = lexer.nextToken();
	}
	
	public void error() {
		throw new RuntimeException("无效的语法");
	}
	
	public void eat(Token.Type tokenType) {
		if (currToken.tokenType == tokenType) {
			currToken = lexer.nextToken();
		} else {
			error();
		}
	}
	
	public Object factor() {
		// 现阶段默认固定整数
		Token token = this.currToken;
		eat(Token.Type.INTEGER);
		return token.value;
	}
	
	
	/** 
	 * 该函数校验（验证）标记符序列是否与预期序列一致，比如，INTEGER -> PLUS -> INTEGER。
	 * 在结构确认无误后，它将加号左边标记符的值与其右边标记符值相加，生成（表达式的）结果，
	 * 这样，就将你传给解释器的表达式，成功计算出算术表达式的结果。 
	 */
	public Object expr() {
		Object result = factor();
		while (currToken.tokenType == Token.Type.OP_MULTIPLY || currToken.tokenType == Token.Type.OP_DIVISION) {
			if (currToken.tokenType == Token.Type.OP_MULTIPLY) {
				eat(Token.Type.OP_MULTIPLY);
				result = Integer.parseInt(String.valueOf(result)) * Integer.parseInt(String.valueOf(factor()));
			}
			else if (currToken.tokenType == Token.Type.OP_DIVISION) {
				eat(Token.Type.OP_MULTIPLY);
				result = Integer.parseInt(String.valueOf(result)) * Integer.parseInt(String.valueOf(factor()));
			}
		}
		return result;
	}
	
}
