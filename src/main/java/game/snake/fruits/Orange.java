package game.snake.fruits;

import java.awt.Graphics;

import game.snake.Fruit;

/**
 * 桔子
 */
public class Orange extends Fruit {

	public Orange(int r, int c) {
		super(r, c);
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(allFruits,colIndex*UNIT, rowIndex*UNIT,colIndex*UNIT+UNIT, rowIndex*UNIT+UNIT, 4, 0, 4+30, 0 + 30, null);
	}

}
