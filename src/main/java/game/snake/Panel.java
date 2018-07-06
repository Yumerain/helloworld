package game.snake;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Panel extends JPanel implements Runnable{
	
	private static final long serialVersionUID = -3509619511193661382L;

	private Game game;
	
	//private Font font = new Font("Î¢ÈíÑÅºÚ", Font.BOLD, 16);
	
	public Panel(Game game){
		this.game = game;
	}
	
	// Æô¶¯Ãæ°åË¢ÐÂ
	public void startUpdate(){
		new Thread(this).start();
	}
	
	@Override
	public void paint(Graphics g) {
		//g.setFont(font);
		// Êä³ö»­Ãæµ½»­°å
		game.draw(g);
		// »­±ß¿ò
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, this.getWidth()-1, this.getHeight()-1);
	}

	public void run(){
		while(true){
			// Ë¢ÐÂ´°¿Ú
			this.repaint();
			try {
				Thread.sleep(30);
			} catch (Exception e) {
			}			
		}
	}	
	
}
