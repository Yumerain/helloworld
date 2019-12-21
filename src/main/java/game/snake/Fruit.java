package game.snake;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public abstract class Fruit extends Block {

	protected BufferedImage allFruits;

	// 单元大小
	public static final int UNIT = 30;
	
	/** 跟随图片大小固定30像素，由于是图片，颜色没有意义，随意即可 */
	public Fruit(int r, int c) {
		super(UNIT, Color.white, r, c);
		try {
			allFruits = ImageIO.read(getClass().getResourceAsStream("/game/snake/res/fruits.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public abstract void draw(Graphics g);
	
}
