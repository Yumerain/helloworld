package game.snake;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameKeyListener implements KeyListener{

	// 贪食蛇的引用
	private Snake snake;
	
	// 游戏的引用
	private Game game;
	
	public GameKeyListener(Snake snake, Game game){
		this.snake = snake;
		this.game = game;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case 37: // 左
			snake.turnLeft();
			break;
		case 40: // 下
			snake.turnDown();
			break;
		case 39: // 右
			snake.turnRight();
			break;
		case 38: // 上
			snake.turnUp();
			break;
		case 10: // 激活/暂停
			game.toggleActive();
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

}
