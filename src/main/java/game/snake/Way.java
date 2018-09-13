package game.snake;

import java.awt.Point;
import java.util.ArrayDeque;
import java.util.List;

/**
 * 路线、路径
 * @author rainleaf
 *
 */
public class Way {
	
	private int maxRow, maxCol;
	private boolean find = false;
	private boolean[][] mark;
	private ArrayDeque<Point> steps;

	public boolean isEmpty() {
		return false;
	}

	public char next() {
		return 0;
	}

	public void search(List<Block> body, Block fruit, int rows, int cols) {
		maxRow = rows;
		maxCol = cols;
		steps = new ArrayDeque<>(rows * cols);
		mark = new boolean[rows][cols];
		int[][] map = new int[rows][cols];
		// 蛇身标记为障碍：1
		for (int i = 0; i < body.size(); i++) {
			Block block = body.get(i);
			map[block.getRowIndex()][block.getColIndex()] = 1;
		}
		// 水果标记为目标：2
		map[fruit.getRowIndex()][fruit.getColIndex()] = 2;
		
		// 寻找从蛇头到水果之间的路径
		// 如果没找到则向四个方向搜索
		dfs(body.get(0).getRowIndex(), body.get(0).getColIndex(), map);
	}
	
	/**
	 * DFS寻路
	 * @param row起始行坐标
	 * @param col起始列坐标
	 * @param map
	 */
	public void dfs(int row, int col, int[][] map) {
		// 越界判断、曾经访问过
		if(row < 0 || col <0 || row > maxRow-1 || col > maxCol-1 || mark[row][col]) {
			// 当前线路行不通，移出路径
			//steps.removeLast();
			return;
		}
		// 标记已经访问
		mark[row][col] = true;
		
		int entity = map[row][col];
		//障碍
		if(entity == 1) {
			return;
		}
		// 找到目标
		if(entity == 2) {
			//steps.addLast(new Point(row, col));
			find = true;
			return;
		}
		// 当前线路可以同行则加入路径
		//steps.addLast(new Point(row, col));
		// entity == 0
		// 如果没找到则向四个方向搜索
		if(!find)dfs(row+1, col, map);
		if(!find)dfs(row, col+1, map);
		if(!find)dfs(row-1, col, map);
		if(!find)dfs(row, col-1, map);
	}

}
