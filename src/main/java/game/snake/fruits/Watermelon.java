package game.snake.fruits;

import java.awt.Graphics;

import game.snake.Fruit;

/**
 * 西瓜
 */
public class Watermelon extends Fruit {

	public Watermelon(int r, int c) {
		super(r, c);
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(allFruits,colIndex*UNIT, rowIndex*UNIT,colIndex*UNIT+UNIT, rowIndex*UNIT+UNIT, 212, 0, 212+30, 0 + 30, null);
	}

}
