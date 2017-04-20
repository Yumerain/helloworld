package myjava;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Locale;

public class CommandLine {
	
	public static void main(String[] args) {
		Locale locale = Locale.getDefault();
		System.out.println(locale.getLanguage());
	}

	private BufferedReader reader;

	public CommandLine(){
		try {
			reader = new BufferedReader(new InputStreamReader(System.in, System.getProperty("file.encodin")));
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
			} while (line != null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
