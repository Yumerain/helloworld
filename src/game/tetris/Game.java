package game.tetris;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

/**
 * 游戏逻辑流程实现
 * @author zhangyu
 */
public class Game extends Thread implements KeyListener{
	
	/** 游戏的时钟刻度滴答 */
	public final long TICK = 10;
	
	// 游戏格子的行数
	private int rows = 26;
	// 游戏格子的列数
	private int cols = 16;
	
	// 画面宽度
	private int width;
	// 画面高度
	private int height;
	
	// 状态
	private Status status = Status.START;
	
	//	 分数
	private int score = 0;
	
	// 等级：等级越高下落速度越快
	private int level = 1;
	
	// 游戏刷新间隔
	private int interval = 0;
	
	// 单元大小
	public static final int UNIT = 30;
	// 侧面信息面 板宽
	public static final int SIDE = 100; 
	
	private ModelFactory modelFactory = ModelFactory.getInstance();
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
		model = modelFactory.buildRandom(rows, cols);
		nextModel = modelFactory.buildRandom(rows, cols);
	}
	
	
	// 输出画面
	public void draw(Graphics g){
		// 清空(填充)原有画面
		g.setColor(Color.black);
		g.fillRect(0, 0, width, height);
		// 面板方框
		g.setColor(Color.white);
		g.drawRect(0, 0, cols * UNIT, height);
		// 正在下落的模块
		model.draw(g);
		// 下一个将要出现的模块
		nextModel.draw(g);
		// 画出游戏分数
		g.drawString("分数："+score, width - 80, 30);
		// 结束
		if(status == Status.OVER){
			g.setColor(Color.red);
			g.drawString("Game Over", 300, 200);			
		}
	}
	
	// 线程
	public void run(){
		try {
			while(true){
				update();
				Thread.sleep(TICK);
			}
		} catch (Exception e) {
		}
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	//-----------------------------KeyListener的实现-----------------------------//
	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case 38: // 上/w
		case 87:
			break;
		case 40: // 下/s
		case 83:
			model.moveDown();
			break;
		case 37: // 左/a
		case 65:
			model.moveLeft();
			break;
		case 39: // 右/d
		case 68:
			model.moveRight();
			break;
		case 74: 	// A(j)
			model.anticlockwiseRotating();
			break;
		case 75:   // B(k)
			model.clockwiseRotating();
			break;
		case 70:   // 功能/选择(f)
			break;
		case 72:   // 开始/暂停(h)
			control();
			break;
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
	}
	@Override
	public void keyTyped(KeyEvent e) {
	}
	//-----------------------------KeyListener的实现-----------------------------//
	
	// 状态控制
	private void control() {
		if(status == Status.PAUSE)
			status = Status.PLAYING;
		else if(status == Status.PLAYING)
			status = Status.PAUSE;
		else if(status == Status.START)
			status = Status.PLAYING;
	}
	
	// 进度更新
	private void update() {
		interval++;
		// 当前模块自动下降一格
		if(interval >= level * TICK){
			model.moveDown();
			interval = 0;
		}
	}
	
	/** 状态定义 */
	enum Status{
		START,
		PAUSE,
		PLAYING,
		OVER
	}
}
