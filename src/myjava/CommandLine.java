package myjava;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class CommandLine {
	
	public static void main(String[] args) {
		new CommandLine().start();
	}

	private BufferedReader reader;

	public CommandLine(){
		try {
			reader = new BufferedReader(new InputStreamReader(System.in, System.getProperty("file.encoding")));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public void start(){
		try {
			reader = new BufferedReader(new InputStreamReader(System.in, "GBK"));
			String line = null;
			do {
				System.out.print(">");
				line = reader.readLine();
				if ("exit".equals(line)) {
					System.out.println("== Bye!Bye! ==");
					break;
				}
				Lexer lx = new Lexer(line);
				Interpreter ipt = new Interpreter(lx);
				System.out.println(ipt.expr());
			} while (line != null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
