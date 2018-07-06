package ai.bp;

public class BpMain {
	
	public static void main(String[] args) {
		runEpoch(false, 90000);
	}
	
	public static void runEpoch(boolean debug, int count) {
		double[][] inputs = new double[][] {{0, 1},{1, 0},{0, 0},{1, 1}};
		double[][] outputs = new double[][] {{0}, {0}, {0}, {1}};
		
		Network net = new Network(2, new int[] {2, 6, 1});
		for (int i = 0; i < inputs.length; i++) {
			double[] ds = net.compute(inputs[i]);
			System.out.println(ds[0]);
		}

		System.out.println("==================");
		Learning learn = new Learning(net);
		for (int i = 0; i < count; i++) {
			double err = learn.trainEpoch(inputs, outputs);
			if(debug && i%1000==0)System.out.println(err);
		}
		System.out.println("==================");
		
		for (int i = 0; i < inputs.length; i++) {
			double[] ds = net.compute(inputs[i]);
			System.out.println(ds[0]);
		}
	}
	
	
	public static void run(boolean debug, int count) {
		double[] input = new double[] {0, 1};
		double[] output = new double[] {0};
		
		Network net = new Network(2, new int[] {2, 6, 1});
		double[] ds = net.compute(input);
		System.out.println(ds[0]);

		System.out.println("==================");
		Learning learn = new Learning(net);
		for (int i = 0; i < count; i++) {
			double err = learn.train(input, output);
			if(debug && i%1000==0)System.out.println(err);
		}
		System.out.println("==================");
		
		ds = net.compute(new double[] {0, 1});
		System.out.println(ds[0]);
	}

}
