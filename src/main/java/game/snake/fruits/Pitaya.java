package game.snake.fruits;

import java.awt.Graphics;

import game.snake.Fruit;

/**
 * 火龙果
 */
public class Pitaya extends Fruit {

	public Pitaya(int r, int c) {
		super(r, c);
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(allFruits,colIndex*UNIT, rowIndex*UNIT,colIndex*UNIT+UNIT, rowIndex*UNIT+UNIT, 301, 0, 301+30, 0 + 30, null);
	}

}
