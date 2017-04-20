package game.snake;


public class AI {
	

	/** 思考 */
	public void think(Snake snake, Block fruit, int rows, int cols) {
		int fruitRow = fruit.getRowIndex();
		int fruitCol = fruit.getColIndex();
		switch (snake.direction) {
		case '↑':
		case '↓':
			if(snake.row==fruitRow){
				// 垂直移动时，水平方向与水果相同需要左右转弯
				if(snake.col>fruitCol){
					snake.turnLeft();
				}else{
					snake.turnRight();
				}
			}else if(snake.direction=='↑' && snake.row<fruitRow){
				// 如果蛇头在向上走，并且蛇头在水果的上方
				if(snake.col>fruitCol){
					snake.turnLeft();
				}else{
					snake.turnRight();
				}
			}else if(snake.direction=='↓' && snake.row>fruitRow){
				// 如果蛇头在向下走，并且蛇头在水果的下方
				if(snake.col>fruitCol){
					snake.turnLeft();
				}else{
					snake.turnRight();
				}
			}
			break;
		case '←':
		case '→':
			if(snake.col==fruitCol){
				// 水平移动时，当垂直方向与水果相同需要上下转弯
				if(snake.row>fruitRow){
					snake.turnUp();
				}else{
					snake.turnDown();
				}
			} else if(snake.direction=='←' && snake.col<fruitCol){
				// 如果蛇头在向左走，并且蛇头在水果的左边
				if(snake.row>fruitRow){
					snake.turnUp();
				}else{
					snake.turnDown();
				}
			}else if(snake.direction=='→' && snake.col>fruitCol){
				// 如果蛇头在向右走，并且蛇头在水果的右边
				if(snake.row>fruitRow){
					snake.turnUp();
				}else{
					snake.turnDown();
				}
			}
			break;			
		}
		// 蛇转完弯后，检查几步之后会不会撞到自身
		checkInStep(step, snake, rows, cols);
	}

	public static int step = 20;
	
	/***
	 * step步后，会不会撞到自身
	 * @param step
	 */
	public void checkInStep(int step, Snake snake, int rows, int cols){
		int currBoomStep = snake.checkBoomStep(step, snake.direction);
		int nextBoomStep = snake.checkBoomStep(step, snake.nextDirection);
		if(currBoomStep==-1 && nextBoomStep>=1){
			System.out.println("currBoomStep:" + currBoomStep);
			System.out.println("nextBoomStep:" + nextBoomStep);
			System.out.println("-------------------------");
			snake.keepDierection();
		}
		
		
	}
	
	/**
	 * 
	 * @param snake
	 * @param offerRow 只能取值：0或+1或-1
	 * @param offerCol 只能取值：0或+1或-1
	 * @return
	 */
	public BB checkBoom(Snake snake, int offerRow, int offerCol){
		boolean boom = false;
		int count = 0;
		for (int k = 1; k < snake.body.size()/2; k++) {
			count++;
			if(snake.check(snake.row + offerRow*k, snake.col + offerCol*k)==true){
				boom = true;
				break;
			}
		}
		BB bb = new BB();
		bb.boom = boom;
		bb.count = count;
		return bb;
	}
}
