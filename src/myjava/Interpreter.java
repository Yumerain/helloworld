package myjava;

public class Interpreter {
	
	public String text;
	public int pos = 0;
	public char currChar;
	
	public Interpreter(String text){
		this.text = text;
		this.currChar = text.charAt(pos);
	}
	
	public Token nextToken() {
		while(pos < text.length()){
			
			currChar = text.charAt(pos++);
			
			// 空白字符
			if (currChar == ' ' || currChar == '\t' || currChar == '\r' || currChar == '\n') {
				continue;
			}
			
			// 整数
			if (currChar >= '0' && currChar <= '9') {
				return new Token(Token.Type.INTEGER, Integer.valueOf(String.valueOf(currChar)));
			}
			
			// 算术运算符
			if (Operator.isMathOperator(currChar)) {
				switch (currChar) {
				case '+': 	return new Operator(Token.Type.OP_PLUS, currChar);
				case '-': 	return new Operator(Token.Type.OP_MINUS, currChar);
				case '*': 	return new Operator(Token.Type.OP_MULTIPLY, currChar);
				case '/': 	return new Operator(Token.Type.OP_DIVISION, currChar);
				}
			}
			
			throw new RuntimeException("Error parsing input");
		}
		
		// 结束
		return new Token(Token.Type.EOF, null);
	}
	
	
	/** 
	 * 该函数校验（验证）标记符序列是否与预期序列一致，比如，INTEGER -> PLUS -> INTEGER。
	 * 在结构确认无误后，它将加号左边标记符的值与其右边标记符值相加，生成（表达式的）结果，
	 * 这样，就将你传给解释器的表达式，成功计算出算术表达式的结果。 
	 */
	public Object expr() {
		Token left = nextToken();
		
		Operator op = (Operator)nextToken();
		
		Token right = nextToken();
		return op.eval(left, right);
	}
	
}
