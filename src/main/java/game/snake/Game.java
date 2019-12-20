package game.snake;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game extends Thread{
	
	private Color[] colors = new Color[]{
		Color.red,Color.green,Color.blue,
		Color.yellow,Color.orange,Color.cyan
	};
	
	// 随机数生成器
	private Random random = new Random();
	
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
	private List<Block> fruits = new ArrayList<>();
	
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
		// 创建随机的水果
		createFruit();
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
		for (int i = 0; i < fruits.size(); i++) {
			Block fruit = fruits.get(i);
			g.setColor(fruit.getColor());
			g.fillRect(fruit.getColIndex()*UNIT, fruit.getRowIndex()*UNIT, fruit.getUnit(), fruit.getUnit());
		}
		
		// 输出文字：请按回车键开始游戏
		
		//g.drawString("请按回车键开始游戏", 60, 100);
		
		// 画出蛇的身体
		snake.draw(g);
		
		// 画出游戏分数
		g.setColor(Color.blue);
		g.drawString("当前总计得分数："+score, 10, 40);
		g.drawString("地图上水果个数："+fruits.size(), 10, 20);
		
		// 画出文字：Game Over
		g.setColor(Color.red);
		if(gameOver){
			g.drawString("Game Over，按回车键重新开始", 40, 60);			
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
		while(true){
			if(active == true && gameOver == false){
				int result = snake.move(fruits);	// 普通移动
				//int result = snake.autoMove(fruit);		// 智能移动
				if(result == 1){
					score += 100;
					if(score >= clearScore){
						clear = true;
						break;
					}
					if(fruits.isEmpty()){
						createFruit();
					}
				}else if(result == -1){
					gameOver = true;
				}
			}
			try {
				Thread.sleep(100);
			} catch (Exception e) {
			}
		}
	}
	
	// 随机创建几个位置的水果
	public void createFruit(){
		// 地图剩余格子
		int remain = cols * rows - snake.body.size() - fruits.size();
		int count = random.nextInt(5);
		// 生成的水果数量不能超过剩余格子
		if(count>remain){
			count = remain;
		}
		if(count==0){
			count = 1;
		}
		for (int i = 0; i < count; i++) {
			int r = rows;
			int c = cols;
			// 保证行位置与列位置在游戏的行列之中，并且不与蛇的身体重复，不与已有水果重复
			while(r >= rows || c >= cols || snake.check(r, c) || fruitCheck(r, c)){
				r = random.nextInt(rows);
				c = random.nextInt(cols);
			}
			int rdColor = 6;
			while(rdColor >5){
				rdColor = (int)(Math.random()*10);
			}
			Block block = new Block(UNIT, colors[rdColor], r, c);	
			fruits.add(block);
		}	
	}
	
	public boolean fruitCheck(int r, int c){
		for (int i = 0; i < fruits.size(); i++) {
			Block item = fruits.get(i);
			if(c==item.getColIndex() && r==item.getRowIndex()){
				return true;
			}
		}
		return false;
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
