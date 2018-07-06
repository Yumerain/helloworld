package game.tetris;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * 绘画面板
 * @author zhangyu
 */
public class Panel extends JPanel implements Runnable{
	
	private static final long serialVersionUID = -3509619511193661382L;

	/** FPS计时最长0.5秒(500毫秒，500*1000000纳秒) */
	private final long MAX_FPS_INTERVAL = 500*1000000;
	/** 最大FPS数 */
	private final int MAX_FPS = 250;
	/** 显示FPS的字体 */
	private final Font fpsFont = new Font("Dialog", Font.LAYOUT_LEFT_TO_RIGHT, 20);
	/** 是否显示FPS */
	private boolean showFPS = true;
	/** 计算FPS的变量：当前FPS、帧计数 */
	private int currFPS, frameCount;
	/** 计算FPS的变量：时间数、计时开始时间、每次绘制所花时间 */
	private long fpstIntervalTime, startTime, offsetTime;
	
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
			g.setColor(Color.white);
			g.drawString("FPS:" + currFPS, 10, 25);
		}
	}
	
	/** 计算FPS */
	private void tickFrames(){
		frameCount++;
		fpstIntervalTime += offsetTime;
		// 时间达到了需要计算的时长
		if (fpstIntervalTime >= MAX_FPS_INTERVAL) {
			long timeNow = System.nanoTime();
			long elapsedTime = timeNow - startTime;
			currFPS = (int)(frameCount * MAX_FPS_INTERVAL / elapsedTime);	// 时间内绘制的次数
			frameCount = 0;
			fpstIntervalTime = 0;
			startTime = timeNow;
		}
	}

	/** 面板刷新线程 */
	public void run(){
		startTime = System.nanoTime();								// FPS计时开始
		offsetTime = (long)(1.0 / MAX_FPS * MAX_FPS_INTERVAL);		// 按FPS1秒内最大帧数时间的幅度统计FPS
		while(true){
			// 刷新窗口
			this.repaint();
			Thread.yield();	
		}
	}	
	
}
