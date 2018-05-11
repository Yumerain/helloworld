package ai.bp;

import java.io.Serializable;
import java.util.Random;

public class Neuron  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8652952062809005783L;

	private int inputsCount;
	
	protected double[] weights;
	
	protected double output;
	
	protected double bias;
	
	public Neuron(int inputsCount) {
		this.inputsCount = inputsCount;
		this.weights = new double[inputsCount];
		Random rd = new Random();
		for (int i = 0; i < weights.length; i++) {
			weights[i] = rd.nextDouble();			
		}
		bias = rd.nextDouble();
	}
	
	public double compute(double[] inputs) {
		if(inputs == null || inputs.length != inputsCount) {
			throw new RuntimeException("神经元的输入参数为null或参数个数长度不符");
		}
		for (int i = 0; i < inputsCount; i++) {
			output += (weights[i] * inputs[i]);
		}
		output += bias;
		output = active(output);
		return output;
	}
	
	public double active(double num) {
		return 1 / (1 + Math.exp(-1 * num));
	}
	
	public double getOutput()	{
		return this.output;
	}
	
}
