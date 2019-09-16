package web;
import com.jfinal.core.JFinal;

public class WebMain { 
	public static void main(String[] args) {

		JFinal.start("./src/web", 8000, "/");
		
	}
	
}
