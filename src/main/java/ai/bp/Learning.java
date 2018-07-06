package ai.bp;

public class Learning {
	
	// 被训练的目标网络
	private Network network;
	
	// 学习率
	private double learningRate = 0.1;
	// 神经元的误差
	private double[][] neuronsErrors;
	// 神经元个输入链接权重更新值
	private double[][][] weightsUpdates;
	// 神经元的偏置更新
	private double[][] biasesUpdates;
	
	
	public Learning(Network network) {
		this.network = network;
		Layer[] layers = network.layers;
		this.neuronsErrors = new double[layers.length][];
		this.weightsUpdates = new double[layers.length][][];
		this.biasesUpdates = new double[layers.length][];
		for (int i = 0; i < layers.length; i++) {
			Layer layer = layers[i];
			this.neuronsErrors[i] = new double[layer.neurons.length];
			this.weightsUpdates[i] = new double[layer.neurons.length][];
			this.biasesUpdates[i] = new double[layer.neurons.length];
			Neuron[] neurons = layer.neurons;
			for (int k = 0; k < neurons.length; k++) {
				this.weightsUpdates[i][k] = new double[neurons[k].weights.length];
			}
		}
	}
	
	/**
	 * 单词训练
	 * @param input 输入值
	 * @param output 期望输出
	 * @return 误差
	 */
	public double train(double[] input, double[] output) {
		// 网络运行指定参数
		network.compute(input);
		// 计算误差
		double error = calcErrors(output);
		// 计算权重更新
		calcUpdates(input);
		// 更新网络各神经元链接权重
		updateNetwork();
		return error;
	}


	public double trainEpoch(double[][] input, double[][] output) {
		if (input.length != output.length) {
			throw new RuntimeException("输入和期望输出数据个数不相等！");
		}
		double errors = 0;
		for (int i = 0; i < input.length; i++) {
			errors += train(input[i], output[i]);
		}
		return errors;
	}

	/**
	 * 计算网络输出值与期望值的误差
	 * @param desiredOutput
	 * @return
	 */
	private double calcErrors(double[] desiredOutput) {
		// 网络的总误差
		double totalErrors = 0;
		// 取出最后一层存储神经元误差的数组
		
		// 计算输出层（最后一层）的误差
		Layer layer = network.layers[network.layers.length - 1];
		Neuron[] neurons = layer.neurons;
		for (int i = 0; i < neurons.length; i++) {
			// 神经元计算结果与期望值的误差
			double error = desiredOutput[i] - neurons[i].output;
			// 保存神经元误差与期望输出倒数的积（不懂）
			neuronsErrors[network.layers.length - 1][i] = error * derivative(neurons[i].output);
			// 累加误差平方
			totalErrors += error * error;
		}
		
		// 倒序逐层计算误差：计算当前和下一个层之间的误差
		for (int k = network.layers.length - 2; k >= 0; k--) {
			layer = network.layers[k];
			Layer nextLayer = network.layers[k + 1];
			neurons = layer.neurons;
			Neuron[] nextNeurons = nextLayer.neurons;
			// 当前层的神经元
			for (int i = 0; i < neurons.length; i++) {
				double error = 0;
				// “当前神经元的输出”误差与“下一层的各个神经元相应链接权重”
				for (int z = 0; z < nextNeurons.length; z++) {
					error += neuronsErrors[k + 1][z] * nextNeurons[z].weights[i];
				}
				neuronsErrors[k][i] = error * derivative(neurons[i].output);
			}
		}
		
		return totalErrors / 2.0;
	}
	
	private double derivative(double y) {
		return y * (1 - y);
	}

	/**
	 * 计算权重更新
	 * @param input
	 */
	private void calcUpdates(double[] input) {
		// 计算第一层的神经元的权重更新
		// 计算误差分担
		double[] firstLayerErrors = neuronsErrors[0];
		Neuron[] neurons = network.layers[0].neurons;
		for (int i = 0; i < neurons.length; i++) {
			Neuron neuron = neurons[i];
			// 使用学习率调整后的误差
			double fixError = firstLayerErrors[i] * learningRate;
			for (int k = 0; k < neuron.weights.length; k++) {
				// 权重更新
				weightsUpdates[0][i][k] = fixError * input[k];
			}
			// 偏置更新
			biasesUpdates[0][i] = fixError;
		}
		
		// 计算剩余其他层神经元的权重更新
		for (int j = 1; j < network.layers.length; j++) {
			Layer layer = network.layers[j];
			neurons = layer.neurons;
			for (int i = 0; i < neurons.length; i++) {
				Neuron neuron = neurons[i];
				// 使用学习率调整后的误差
				double fixError = neuronsErrors[j][i] * learningRate;
				for (int k = 0; k < neuron.weights.length; k++) {
					// 权重更新：上一层神经元的输出为当前层的输入
					weightsUpdates[j][i][k] = fixError * network.layers[j-1].neurons[k].output;
				}
				// 偏置更新
				biasesUpdates[j][i] = fixError;
			}
		}
	}

	/**
	 * 更新网络个神经元连接权重
	 */
	private void updateNetwork() {
		for (int i = 0; i < network.layers.length; i++) {
			Layer layer = network.layers[i];
			for (int j = 0; j < layer.neurons.length; j++) {
				Neuron neuron = layer.neurons[j];
				for (int k = 0; k < neuron.weights.length; k++) {
					neuron.weights[k] += weightsUpdates[i][j][k];
				}
				neuron.bias += biasesUpdates[i][j];
			}
		}
	}
}
