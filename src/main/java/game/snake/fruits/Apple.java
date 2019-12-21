package game.snake.fruits;

import java.awt.Graphics;

import game.snake.Fruit;

/**
 * 苹果
 */
public class Apple extends Fruit {

	public Apple(int r, int c) {
		super(r, c);
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(allFruits,colIndex*UNIT, rowIndex*UNIT,colIndex*UNIT+UNIT, rowIndex*UNIT+UNIT, 44, 0, 44+30, 0 + 30, null);
	}

}
