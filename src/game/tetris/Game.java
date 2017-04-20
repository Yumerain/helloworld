package game.tetris;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class Game extends Thread{
	
	// 游戏格子的行数
	private int rows = 26;
	// 游戏格子的列数
	private int cols = 16;
	
	// 画面宽度
	private int width;
	// 画面高度
	private int height;
	
	// 表示游戏是否结束
	private boolean gameOver = false;
	
	//	 分数
	private int score = 0;
	
	// 单元大小
	public static final int UNIT = 30;
	// 侧面信息面 板宽
	public static final int SIDE = 100; 
	
	// 正在下降的模块
	private Model model;
	// 下一个将要出现的模块
	private Model nextModel;
	// 已经落下被砌成了的墙
	private List<Block> wall = new ArrayList<Block>(rows * cols);

	public Game(){
		// 宽度要加一个侧面显示信息
		this.width = cols * UNIT + SIDE;
		this.height = rows * UNIT;
		model = createModel();
		nextModel = createModel();
	}
	
	
	// 输出画面
	public void draw(Graphics g){
		// 清空(填充)原有画面
		g.setColor(Color.black);
		g.fillRect(0, 0, width, height);		
		// 正在下落的模块
		//model.draw(g);
		// 下一个将要出现的模块
		//nextModel.draw(g);
		// 画出游戏分数
		g.setColor(Color.white);
		g.drawString("分数："+score, width - 80, 30);
		// 结束
		if(gameOver){
			g.setColor(Color.red);
			g.drawString("Game Over", 300, 200);			
		}
	}
	
	public void run(){
		try {
			while(true){
				Thread.sleep(20);
			}
		} catch (Exception e) {
		}
	}
	
	// 创建键监听事件实例
	public GameKeyListener createKeyListener(){
		return new GameKeyListener();
	}
	
	// 创建模块
	private Model createModel(){
		return  null;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
}
