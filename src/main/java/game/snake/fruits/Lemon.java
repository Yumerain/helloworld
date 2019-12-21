package game.snake.fruits;

import java.awt.Graphics;

import game.snake.Fruit;

/**
 * 柠檬
 */
public class Lemon extends Fruit {

	public Lemon(int r, int c) {
		super(r, c);
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(allFruits,colIndex*UNIT, rowIndex*UNIT,colIndex*UNIT+UNIT, rowIndex*UNIT+UNIT, 88, 0, 88+30, 0 + 30, null);
	}

}
