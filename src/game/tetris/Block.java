package game.tetris;

import java.awt.Color;

/** 单元：小节、小块 */
public class Block {
	
	public Block(Color color, int r, int c){
		this.color = color;
		this.rowIndex = r;
		this.colIndex = c;
	}
	// 单元颜色
	private Color color;
	// 单元格所在的行
	private int rowIndex;
	// 单元格所在的列
	private int colIndex;
	
	public void setColor(Color color){
		this.color = color;
	}
	public Color getColor(){
		return this.color;
	}
	
	public int getRowIndex() {
		return rowIndex;
	}
	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}
	public int getColIndex() {
		return colIndex;
	}
	public void setColIndex(int colIndex) {
		this.colIndex = colIndex;
	}
	
	

}
