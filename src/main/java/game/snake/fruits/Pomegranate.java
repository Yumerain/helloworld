package game.snake.fruits;

import java.awt.Graphics;

import game.snake.Fruit;

/**
 * 石榴
 */
public class Pomegranate extends Fruit {

	public Pomegranate(int r, int c) {
		super(r, c);
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(allFruits,colIndex*UNIT, rowIndex*UNIT,colIndex*UNIT+UNIT, rowIndex*UNIT+UNIT, 170, 0, 170+30, 0 + 30, null);
	}

}
