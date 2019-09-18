import java.text.DecimalFormat;
import java.util.Random;

import com.jfinal.template.Engine;

public class HelloMain {
	
	public static void main(String[] args) {
		
	}
	
	public static void script(String[] args) {
		Engine engine = Engine.use();
 
        engine.setDevMode(true);
        engine.setToClassPathSourceFactory();
        
        //engine.getTemplate();
		
	}
	
	public static void trade() {

		double winP = 0.350;
		double loseP = 1 - winP;
		
		double tp = 0.20;
		double sl = 0.10;
		
		double amt = 100;

		DecimalFormat df = new DecimalFormat("0.00");
		DecimalFormat df2 = new DecimalFormat("0.00000");
		Random rd = new Random();
		
		for (int i = 0; i < 100; i++) {
			//double rate = (winP * tp - loseP * sl) / (tp * sl);
			double rate = winP / sl - loseP / tp;
			double input = amt * rate;
			
			if (rd.nextBoolean()) {
				amt += input * tp;
				System.out.println(i + "，比例：" + df2.format(rate) + "，赢，资产：" + df.format(amt));
			} else {
				amt -= input * sl;
				System.out.println(i + "，比例：" + df2.format(rate) + "，输，资产：" + df.format(amt));
			}
			
		}
	}
	
	public static void du() {
		double winP = 0.5;
		double loseP = 1 - winP;
		double b = 1.5;
		double amt = 100;

		DecimalFormat df = new DecimalFormat("0.00");
		DecimalFormat df2 = new DecimalFormat("0.000000");
		Random rd = new Random();
		
		for (int i = 0; i < 1000; i++) {
			double rate = (winP * b - loseP) / b;
			
			double input = amt * rate;
			if (rd.nextBoolean()) {
				amt += input * b;
				System.out.println(i + "，比例：" + df2.format(rate) + "，赢，资产：" + df.format(amt));
			} else {
				amt -= input;
				System.out.println(i + "，比例：" + df2.format(rate) + "，输，资产：" + df.format(amt));
			}
			
		}
	}
	
}
