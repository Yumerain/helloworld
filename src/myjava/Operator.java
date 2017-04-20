package myjava;

import java.util.HashMap;

public class Operator extends Token {
	
	public Operator(Type tokenType, Object value) {
		super(tokenType, value);
	}

	public static boolean isMathOperator(char c) {
		switch (c) {
		case '+':
		case '-':
		case '*':
		case '/':
			return true;
		default:
			return false;
		}
	}

	public Object eval(Token left, Token right) {
		switch ((char)value) {
		case '+':	return (int)left.value + (int)right.value;
		case '-':		return (int)left.value - (int)right.value;
		case '*':		return (int)left.value * (int)right.value;
		case '/':		return (int)left.value / (int)right.value;
		}
		throw new RuntimeException("Error parsing input");
	}
}
