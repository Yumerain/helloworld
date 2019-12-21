package game.snake.fruits;

import java.awt.Graphics;

import game.snake.Fruit;

/**
 * 山竹
 */
public class Mangosteen extends Fruit {

	public Mangosteen(int r, int c) {
		super(r, c);
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(allFruits,colIndex*UNIT, rowIndex*UNIT,colIndex*UNIT+UNIT, rowIndex*UNIT+UNIT, 399, 0, 399+30, 0 + 30, null);
	}

}
