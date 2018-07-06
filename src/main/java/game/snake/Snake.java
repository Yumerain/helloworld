package game.snake;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class Snake {
	
	public static final int START_ROW = 1;
	public static final int START_COL = 16;
	
	private AI ai = new AI();
	
	// 游戏格子的行数
	private int rows;
	// 游戏格子的列数
	private int cols;

	// 蛇的身体
	public List<Block> body = new ArrayList<Block>();
	
	// 蛇前进的方向：← ↑ → ↓ 
	public char direction = '↓';
	// 下一个要前进的方向
	public char nextDirection = '↓';
	
	// 蛇头的行位置
	public int row;
	
	// 蛇头的列位置
	public int col;
	
	public Snake(int rs, int cs){
		// 游戏格子大小
		this.rows = rs;
		this.cols = cs;
		init();
	}
	
	public void init() {
		// 蛇前进的方向：← ↑ → ↓ 
		this.direction = '←';
		this.nextDirection = '←';
		// 蛇头起始位置
		this.row = START_ROW;
		this.col = START_COL;	
		// 实例化出蛇的4个初始关节
		Block header = new Block(Game.UNIT, Color.black, START_ROW, START_COL);
		Block b1 = new Block(Game.UNIT, Color.gray, START_ROW, START_COL+1);
		Block b2 = new Block(Game.UNIT, Color.gray, START_ROW, START_COL+2);
		Block b3 = new Block(Game.UNIT, Color.gray, START_ROW, START_COL+3);
		body.clear();
		body.add(header);
		body.add(b1);
		body.add(b2);
		body.add(b3);
	}
	
	// 画出蛇的身体
	public void draw(Graphics g){
		for(int i=0; i<body.size(); i++){
			Block block = body.get(i);
			g.setColor(block.getColor());
			g.fillRect(block.getColIndex()*Game.UNIT, block.getRowIndex()*Game.UNIT, block.getUnit(),  block.getUnit());
		}
	}
	
	/**
	 * AI，自主移动
	 */
	public int autoMove(Block fruit){
		// 智能判断一个下方向
		ai.think(this, fruit, rows, cols);
		return move(fruit);
	}
	
	/**
	 * 贪食蛇的移动方法
	 * @param fruit
	 * @return 1-吃到水果；2-普通移动
	 */
	public int move(Block fruit){
		// 跟据蛇的前进方向，判断出蛇头的下一个新位置
		switch (nextDirection) {
		case '←':
			this.col--;
			break;
		case '↑':
			this.row--;
			break;
		case '→':
			this.col++;
			break;
		case '↓':
			this.row++;
			break;
		}
		direction = nextDirection;
		// 操作蛇本身的变化
		// 创新的蛇头
		Block newHeader = new Block(Game.UNIT, Color.black, row, col);
		// 把旧的蛇头的颜色改成身体的颜色
		body.get(0).setColor(Color.gray);		
		// 蛇长长一节：在蛇头前方，跟据蛇的前进方向插入一个新的蛇头
		body.add(0, newHeader);
		
		// ①如果新的蛇头位置与水果相同，则蛇吃到了水果，长长一节
		if(col == fruit.getColIndex() && row == fruit.getRowIndex()){
			// 吃到水果，直接返回，不用移除蛇尾
			return 1;
		}		
		// 新增了一个蛇头后，移除蛇尾，表示蛇前进了一格
		body.remove(body.size() - 1);
		
		// ②蛇前进后判定蛇是否撞墙
		// 判定蛇是否撞墙，撞到就死了
		if(col>=cols || row>=rows || col<0 || row <0) return -1;
		// 判定蛇是否撞到自己的身体，撞到就死了
		for (int i = 1; i < body.size(); i++) {
			Block item = body.get(i);
			// 遍历蛇身体的每一节与蛇头的位置相比较，如果位置重复，则撞到自己身体了
			if(col==item.getColIndex() && row==item.getRowIndex()){
				return -1;
			}
		}
		
		// ③没撞死，普通移动
		return 0;		
	}
	
	/**
	 * 检查水果位置是否与自己的身体重复
	 * @param r
	 * @param c
	 * @return 重复返回true, 否则返回false
	 */
	public boolean check(int r, int c){
		for (int i = 0; i < body.size(); i++) {
			Block item = body.get(i);
			// 遍历蛇身体的每一节与蛇头的位置相比较，如果位置重复，则撞到自己身体了
			//System.out.println("r=" + r + ",sr=" +item.getColIndex() + ",c=" + c + ",sc=" + item.getRowIndex());
			if(c==item.getColIndex() && r==item.getRowIndex()){
				return true;
			}
		}
		return false;
	}
	
	// 贪食蛇转弯
	public void turnLeft(){if(direction!='→')nextDirection='←';}
	public void turnUp(){if(direction!='↓')nextDirection='↑';}
	public void turnRight(){if(direction!='←')nextDirection='→';}
	public void turnDown(){if(direction!='↑')nextDirection='↓';}
	
	// 保持原来路线
	public void keepDierection(){
		nextDirection = direction;
	}
	
	/**
	 * 检查指定方向step步内，多少步会撞上，-1表示不会撞上
	 * @param step
	 * @return
	 */
	public int checkBoomStep(int step, char dir){
		int count = 0;
		boolean boom = false;
		for (int i = 1; i <= step; i++) {
			count++;
			switch (dir) {
			case '↑':
				if(check(row - i, col)==true){
					boom = true;
				}
				break;
			case '↓':
				if(check(row + i, col)==true){
					boom = true;
				}
				break;
			case '←':
				if(check(row, col - i)==true){
					boom = true;
				}
				break;
			case '→':
				if(check(row, col + i)==true){
					boom = true;
				}
				break;
			}
			if(boom){
				break;
			}
		}
		if(boom){
			return count;
		}else{
			return -1;
		}
	}

	public Block getHeader() {
		return body.get(0);
	}
}
