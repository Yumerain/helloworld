package game.snake.fruits;

import java.awt.Graphics;

import game.snake.Fruit;

/**
 * 菠萝
 */
public class Pineapple extends Fruit {

	public Pineapple(int r, int c) {
		super(r, c);
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(allFruits,colIndex*UNIT, rowIndex*UNIT,colIndex*UNIT+UNIT, rowIndex*UNIT+UNIT, 350, 0, 350+30, 0 + 30, null);
	}

}
