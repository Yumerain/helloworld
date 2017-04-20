package myjava;

public class Token {

	public TokenType tokenType;
	public Object value;
	
	public String toString() {
		return String.format("Token(%s, %s)", tokenType, value);
	}
	
	
}
