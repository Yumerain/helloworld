package myjava;

public class Interpreter {
	
	public String text;
	public int pos = 0;
	public Token currToken = null;
	public char currChar;
	
	public Interpreter(String text){
		this.text = text;
		this.currChar = text.charAt(pos);
	}
	
	public void error(){
		throw new RuntimeException("Error parsing input");
	}

	public void advance(){
		pos++;
		if(pos > text.length() - 1)
			currChar = 0;
		else
			currChar = text.charAt(pos);
	}
	
	public void skipWhitespace(){
		
	}
	
	public Token nextToken() {
		while(currChar > 0){
			
		}
		return null;
	}
	
	public Object expr() {
		currToken = nextToken();
		return null;
	}
	
}
