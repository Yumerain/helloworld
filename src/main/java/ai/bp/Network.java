package ai.bp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Network implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1428282578560418040L;

	private int inputsCount;
	
	protected Layer[] layers;
	
	protected double[] outputs;
	
	/**
	 * <输入层(兼隐藏层)>输入参数个数应与<网络>的相同
	 * 网络最后一个层的输出为整个网络的输出
	 * <其他层>输入参数个数等于<上一层>的神经元数目(输出参数个数)
	 * @param inputsCount 网络的输入参数个数
	 * @param neuronsCounts 各层神经元的个数，数组的长度也即为层的个数
	 */
	public Network(int inputsCount, int[] neuronsCounts) {
		this.inputsCount = inputsCount;
		this.layers = new Layer[neuronsCounts.length];
		// 第一层
		layers[0] = new Layer(inputsCount, neuronsCounts[0]);
		// 第二及之后的层
		for (int i = 1; i < layers.length; i++) {
			layers[i] = new Layer(neuronsCounts[i - 1], neuronsCounts[i]);
		}
	}
	
	public double[] compute(double[] inputs) {
		if(inputs == null || inputs.length != inputsCount) {
			throw new RuntimeException("网络的输入参数为null或参数个数长度不符");
		}
		outputs = inputs;
		// 第一层的输入参数为网络的输入参数
		// 剩下其他层的输入参数为上一层的输出参数
		// 最后一层的输出为网络的输出
		for (int i = 0; i < layers.length; i++) {
			outputs = layers[i].compute(outputs);
		}
		return outputs;
	}
	
	public static void save(Network network) {
		File file = new File("./bin/neurons.bin");
		try {
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(network);
			oos.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Network load() {
		Network network = null;
		File file = new File("./bin/neurons.bin");
		try {
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			network = (Network)ois.readObject();
			ois.close();
			fis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return network;
	}
	
	public double[] getOutputs() {
		return this.outputs;
	}
	
}
