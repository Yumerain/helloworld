package game.snake.fruits;

import java.awt.Graphics;

import game.snake.Fruit;

/**
 * 柿子
 */
public class Persimmon extends Fruit {

	public Persimmon(int r, int c) {
		super(r, c);
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(allFruits,colIndex*UNIT, rowIndex*UNIT,colIndex*UNIT+UNIT, rowIndex*UNIT+UNIT, 128, 0, 128+30, 0 + 30, null);
	}

}
