package game.snake.fruits;

import java.awt.Graphics;

import game.snake.Fruit;

/**
 * 香蕉
 */
public class Banana extends Fruit {

	public Banana(int r, int c) {
		super(r, c);
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(allFruits,colIndex*UNIT, rowIndex*UNIT,colIndex*UNIT+UNIT, rowIndex*UNIT+UNIT, 446, 0, 446+30, 0 + 30, null); 
	}

}
