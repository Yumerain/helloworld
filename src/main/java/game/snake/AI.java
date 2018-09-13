package game.snake;

public class AI {
	
	// 路线
	private Way way = new Way();

	/** 思考 */
	public void think(Snake snake, Block fruit, int rows, int cols) {
		if (way.isEmpty()) {
			// 寻路
			way.search(snake.body, fruit, rows, cols);
		}
		switch (way.next()) {
		case '↑':
			snake.turnUp();
			break;
		case '↓':
			snake.turnDown();
			break;
		case '←':
			snake.turnLeft();
			break;
		case '→':
			snake.turnRight();
			break;
		}
	}

	
}
