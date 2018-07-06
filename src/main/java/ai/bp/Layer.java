package ai.bp;

import java.io.Serializable;

public class Layer  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6000471352108804371L;

	private int inputsCount;
	
	/**
	 * 层的输出个数就是层的神经元个数
	 */
	private int outputsCount;
	
	protected Neuron[] neurons;
	
	protected double[] outputs;
	
	public Layer(int inputsCount, int outputsCount) {
		this.inputsCount = inputsCount;
		this.outputsCount = outputsCount;
		this.neurons = new Neuron[outputsCount];
		for (int i = 0; i < neurons.length; i++) {
			neurons[i] = new Neuron(inputsCount);
		}
		this.outputs = new double[outputsCount];
	}
	
	public double[] compute(double[] inputs) {
		if(inputs == null || inputs.length != inputsCount) {
			throw new RuntimeException("层的输入参数为null或参数个数长度不符");
		}
		for (int i = 0; i < outputsCount; i++) {
			outputs[i] = neurons[i].compute(inputs);
		}
		return outputs;
	}
	
	public double[] getOutputs() {
		return this.outputs;
	}

}
