package game.snake;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Panel extends JPanel implements Runnable{
	
	private static final long serialVersionUID = -3509619511193661382L;

	private Game game;
	
	//private Font font = new Font("微软雅黑", Font.BOLD, 16);
	
	public Panel(Game game){
		this.game = game;
	}
	
	// 启动面板刷新
	public void startUpdate(){
		new Thread(this).start();
	}
	
	@Override
	public void paint(Graphics g) {
		//g.setFont(font);
		// 输出画面到画板
		game.draw(g);
		// 画边框
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, this.getWidth()-1, this.getHeight()-1);
	}

	public void run(){
		while(true){
			// 刷新窗口
			this.repaint();
			try {
				Thread.sleep(10);
			} catch (Exception e) {
			}			
		}
	}	
	
}
