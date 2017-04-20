package game.tetris;

import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

import org.loon.framework.javase.game.core.graphics.LColor;

public class Panel extends JPanel implements Runnable{
	
	private static final long serialVersionUID = -3509619511193661382L;

	/** FPS计时最长1秒(1000毫秒) */
	private final int MAX_FPS_INTERVAL = 1000;
	/** 最大FPS数 */
	private final int MAX_FPS = 500;
	/** 显示FPS的字体 */
	private final Font fpsFont = new Font("Dialog", Font.LAYOUT_LEFT_TO_RIGHT, 20);
	/** 是否显示FPS */
	private boolean showFPS = true;
	/** 计算FPS的变量：当前FPS、时间数、计时开始时间、每次绘制所花时间、帧计数 */
	private int currFPS, fpstIntervalTime, startTime, offsetTime, frameCount;
	
	private Game game;
	
	public Panel(Game game){
		this.game = game;
	}
	
	// 启动面板刷新
	public void startUpdate(){
		new Thread(this).start();
	}
	
	@Override
	public void paint(Graphics g) {
		// 输出画面到画板
		game.draw(g);
		// 显示FPS
		if (showFPS) {
			tickFrames();
			g.setFont(fpsFont);
			g.setColor(LColor.white);
			g.drawString("FPS:" + currFPS, 10, 25);
		}
	}
	
	/** 计算FPS */
	private void tickFrames(){
		frameCount++;
		fpstIntervalTime += offsetTime;
		if (fpstIntervalTime >= MAX_FPS_INTERVAL) {
			int timeNow = (int)System.currentTimeMillis();
			int elapsedTime = timeNow - startTime;
			currFPS = (frameCount / elapsedTime) * MAX_FPS_INTERVAL;	// 1秒内绘制的次数
			frameCount = 0;
			fpstIntervalTime = 0;
			startTime = timeNow;
		}
	}

	/** 面板刷新线程 */
	public void run(){
		startTime = (int)System.currentTimeMillis();								// FPS计时开始
		offsetTime = (int)(1.0 / MAX_FPS * MAX_FPS_INTERVAL);		// 按FPS1秒内最大帧数时间的幅度统计FPS
		while(true){
			// 刷新窗口
			this.repaint();
			Thread.yield();	
		}
	}	
	
}
