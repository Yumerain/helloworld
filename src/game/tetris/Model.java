package game.tetris;

import java.awt.Graphics;

/**
 * 砖块接口
 * @author zhangyu
 */
public interface Model {
	
	/** 逆时针方向旋转 */
	void anticlockwiseRotating();
	
	/** 顺时针方向旋转 */
	void clockwiseRotating();
	
	/** 直接下扔下到底 */
	void dropDown();

	/** 向下移动一格 */
	void moveDown();
	
	/** 向左移动一格 */
	void moveLeft();

	/** 向右移动一格 */
	void moveRight();
	
	/** 画模块自身 */
	void draw(Graphics g);

}
