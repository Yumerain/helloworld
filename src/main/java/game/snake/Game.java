package game.snake;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import game.snake.fruits.Apple;
import game.snake.fruits.Banana;
import game.snake.fruits.Lemon;
import game.snake.fruits.Mangosteen;
import game.snake.fruits.Orange;
import game.snake.fruits.Pear;
import game.snake.fruits.Persimmon;
import game.snake.fruits.Pineapple;
import game.snake.fruits.Pitaya;
import game.snake.fruits.Pomegranate;
import game.snake.fruits.Watermelon;

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
	private List<Fruit> fruits = new ArrayList<>();
	
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
		fruits.clear();
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
			Fruit fruit = fruits.get(i);
			fruit.draw(g);
		}
		
		// 输出文字：请按回车键开始游戏
		
		//g.drawString("请按回车键开始游戏", 60, 100);
		
		// 画出蛇的身体
		snake.draw(g);
		
		// 画出游戏分数
		g.setColor(Color.blue);
		g.drawString("当前总计得分数："+score, 10, 40);
		g.drawString("地图上水果个数："+fruits.size(), 10, 20);
		g.drawString("WSAD或方向键移动、空格或回车暂停开始", 10, height - 30);
		
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
	
	public void update() {
		int result = snake.move(fruits);	// 普通移动
		//int result = snake.autoMove(fruit);		// 智能移动
		if(result == 1){
			score += 100;
			if(score >= clearScore){
				clear = true;
				return;
			}
			if(fruits.isEmpty()){
				createFruit();
			}
		}else if(result == -1){
			gameOver = true;
		}
	}
	
	public void run(){
		while(true){
			if(active && !gameOver && !clear){
				update();
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
		int count = random.nextInt(10);
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
			// 水果类型
			int rdColor = random.nextInt(11);
			switch(rdColor) {
			case 0:
				fruits.add(new Banana(r, c));
				break;
			case 1:
				fruits.add(new Mangosteen(r, c));
				break;
			case 2:
				fruits.add(new Pineapple(r, c));
				break;
			case 3:
				fruits.add(new Pitaya(r, c));
				break;
			case 4:
				fruits.add(new Pear(r, c));
				break;
			case 5:
				fruits.add(new Watermelon(r, c));
				break;
			case 6:
				fruits.add(new Pomegranate(r, c));
				break;
			case 7:
				fruits.add(new Persimmon(r, c));
				break;
			case 8:
				fruits.add(new Lemon(r, c));
				break;
			case 9:
				fruits.add(new Apple(r, c));
				break;
			case 10:
				fruits.add(new Orange(r, c));
				break;
			}
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
