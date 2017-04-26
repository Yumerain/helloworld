package game.tetris.models;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import game.tetris.Block;

public class ModelA extends BaseModel {

	//private int offerX, offerY;
	private int offerRow, offerCol;
	private int rows, cols;

	private List<Block> blocks = new ArrayList<Block>(1);

	public ModelA(int rows, int cols) {
		this.offerCol = 0;
		this.offerRow = 0;
		this.rows = rows;
		this.cols = cols;
		blocks.add(new Block(randomColor(), 0, cols / 2));
	}

	@Override
	public void anticlockwiseRotating() {
	}

	@Override
	public void clockwiseRotating() {
	}

	@Override
	public void dropDown() {
	}

	@Override
	public void moveDown() {
		if(offerRow < rows - 1){
			offerRow += 1;
		}
	}

	@Override
	public void moveLeft() {
		if(offerCol > 0){
			offerCol -= 1;
		}
	}

	@Override
	public void moveRight() {
		if(offerCol < cols - 1){
			offerCol += 1;
		}
	}

	@Override
	public void draw(Graphics g) {
		for (int i = 0; i < blocks.size(); i++) {
			Block block = blocks.get(i);
			g.setColor(Color.white);
			g.drawRect((offerCol + block.getColIndex())*UNIT, (offerRow + block.getRowIndex())*UNIT, UNIT, UNIT);
		}
	}

}
