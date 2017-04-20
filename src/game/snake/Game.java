package game.snake;

import java.awt.Color;
import java.awt.Graphics;

public class Game extends Thread{
	
	private Color[] colors = new Color[]{
		Color.red,Color.green,Color.blue,
		Color.yellow,Color.orange,Color.cyan
	};
	
	// 游戏格子的行数
	private int rows = 20;
	// 游戏格子的列数
	private int cols = 32;
	// 游戏最高分
	private int total;
	// 通关分数
	private int clearScore;
	
	// 画面宽度
	private int width;
	// 画面高度
	private int height;
	
	// 贪食蛇
	private Snake snake;
	
	// 水果
	private Block fruit;
	
	// 表示游戏是否结束
	private boolean gameOver = false;
	// 表示游戏是否通关
	private boolean clear = false;
	// 表示游戏是进行/暂停
	private boolean active = false;
	
	//	 分数
	private int score = 0;
	
	// 单元大小
	public static final int UNIT = 30;
	
	public Game(){
		this.width = cols * UNIT;
		this.height = rows * UNIT;
		snake = new Snake(rows,cols);
		init();
	}

	private void init() {
		score = 0;
		total = (rows*cols-4)*100;
		clearScore = total;
		if(clearScore > total) clearScore = total;
		snake.init();
		// 创建一个位置随机的水果
		fruit = createFruit();
	}
	
	public int getWidth(){
		return this.width;
	}
	public int getHeight(){
		return this.height;
	}
	

	// 输出画面
	public void draw(Graphics g){
		// 清空原有画面
		g.clearRect(0, 0, width, height);
		
		// 画出水果
		g.setColor(fruit.getColor());
		g.fillRect(fruit.getColIndex()*UNIT, fruit.getRowIndex()*UNIT, fruit.getUnit(), fruit.getUnit());
		
		// 输出文字：请按回车键开始游戏
		
		//g.drawString("请按回车键开始游戏", 60, 100);
		
		// 画出蛇的身体
		snake.draw(g);
		
		// 画出游戏分数
		g.setColor(Color.blue);
		g.drawString("分数："+score, 10, 30);
		
		// 画出文字：Game Over
		g.setColor(Color.red);
		if(gameOver){
			g.drawString("Game Over，按回车键重新开始", 300, 200);			
		}
		// 画出文字：恭喜你通关！
		g.setColor(Color.red);
		if(clear){
			g.drawString("恭喜你通关！", 300, 200);			
		}
		if(gameOver==false && active==false){
			g.drawString("按回车键开始", 300, 200);
		}
	}
	
	public void run(){
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
		}
		while(true){
			if(active == true && gameOver == false){
				//int result = snake.move(fruit);	// 普通移动
				int result = snake.autoMove(fruit);		// 智能移动
				if(result == 1){
					score += 100;
					if(score >= clearScore){
						clear = true;
						break;
					}
					fruit = createFruit();
				}else if(result == -1){
					gameOver = true;
				}
			}
			try {
				Thread.sleep(30);
			} catch (Exception e) {
			}
		}
	}
	
	// 创建一个位置随机的水果
	public Block createFruit(){
		int r = rows;
		int c = cols;
		// 保证行位置与列位置在游戏的行列之中，并且不与蛇的身体重复
		while(r >= rows || c >= cols || snake.check(r, c)){
			r = (int)(Math.random()*10*rows);
			c = (int)(Math.random()*10*cols);
		}
		int rd = 6;
		while(rd >5){
			rd = (int)(Math.random()*10);
		}
		Block block = new Block(UNIT, colors[rd], r, c);		
		return block;
	}
	
	// 创建键监听事件实例
	public GameKeyListener createKeyListener(){
		return new GameKeyListener(snake, this);
	}

	public void toggleActive() {
		if(gameOver==true){
			init();
			gameOver = false;
			active = true;
		}else{
			active = !active;
		}
	}
}
