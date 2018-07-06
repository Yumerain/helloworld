package myjava;

public class Lexer {
	
	protected String text;
	
	protected int pos;
	
	protected char currChar;
	
	public Lexer(String text) {
		this.text = text;
		this.pos = 0;
		this.currChar = text.charAt(0);
	}
	
	public void error() {
		throw new RuntimeException("无效的字符");
	}
	
	public void advance() {
		pos++;
		if (pos >= text.length()) {
			this.currChar = '\0';
		} else {
			this.currChar = text.charAt(pos);
		}
	}
	
	public void skipWiteSpace() {
		while (currChar != '0' && isSpace(currChar)) {
			advance();
		}
	}
	
	public int getInteger() {
		StringBuilder sb = new StringBuilder();
		while (currChar != '\0' && isNumber(currChar)) {
			sb.append(currChar);
			advance();
		}
		return Integer.parseInt(sb.toString());
	}
	
	public Token nextToken() {
		while(currChar != '\0') {
			if (isSpace(currChar)) {
				skipWiteSpace();
				continue;
			}
			if (isNumber(currChar)) {
				return new Token(Token.Type.INTEGER, getInteger());
			}
			if (currChar == '*') {
				advance();
				return new Token(Token.Type.OP_MULTIPLY, currChar);
			}
			if (currChar == '/') {
				advance();
				return new Token(Token.Type.OP_DIVISION, currChar);
			}
			error();
		}
		// 结束
		return new Token(Token.Type.EOF, null);
	}
	
	/** 是空白字符 */
	private boolean isSpace(char ch) {
		return currChar == ' ' || currChar == '\t' || currChar == '\r' || currChar == '\n';
	}
	
	/** 是数字【0-9】 */
	private boolean isNumber(char ch) {
		return currChar >= '0' && currChar <= '9';
	}

}
