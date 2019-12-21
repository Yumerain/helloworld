package game.snake.fruits;

import java.awt.Graphics;

import game.snake.Fruit;

/**
 * 雪梨
 */
public class Pear extends Fruit {

	public Pear(int r, int c) {
		super(r, c);
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(allFruits,colIndex*UNIT, rowIndex*UNIT,colIndex*UNIT+UNIT, rowIndex*UNIT+UNIT, 260, 0, 260+30, 0 + 30, null);
	}

}
