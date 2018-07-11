package game.tetris;

import game.tetris.models.ModelA;

public class ModelFactory {

	public static ModelFactory getInstance() {
		return new ModelFactory();
	}

	public Model buildRandom(int rows, int cols) {
		return new ModelA(2, 2);
	}

}
