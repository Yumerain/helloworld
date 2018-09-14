package game.snake;

import java.util.LinkedList;
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
	private LinkedList<Character> steps;

	public boolean isEmpty() {
		return steps == null || steps.isEmpty();
	}

	public char next() {
		return steps.pollFirst();
	}

	public void search(List<Block> body, Block fruit, int rows, int cols) {
		maxRow = rows;
		maxCol = cols;
		find = false;
		steps = new LinkedList<Character>();
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
		int row = body.get(0).getRowIndex();
		int col = body.get(0).getColIndex();
		if(!find) {
			steps.add('↓');
			dfs(row+1, col, map);
		}
		if(!find) {
			steps.add('→');
			dfs(row, col+1, map);
		}
		if(!find) {
			steps.add('↑');
			dfs(row-1, col, map);
		}
		if(!find) {
			steps.add('←');
			dfs(row, col-1, map);
		}
		System.out.println(steps);
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
			steps.removeLast();
			return;
		}
		// 标记已经访问
		mark[row][col] = true;
		
		int entity = map[row][col];
		//障碍
		if(entity == 1) {
			steps.removeLast();
			return;
		}
		// 找到目标
		if(entity == 2) {
			find = true;
			return;
		}
		// 当前线路可以同行则加入路径
		// entity == 0
		// 如果没找到则向四个方向搜索
		if(!find) {
			steps.add('↓');
			dfs(row+1, col, map);
		}
		if(!find) {
			steps.add('→');
			dfs(row, col+1, map);
		}
		if(!find) {
			steps.add('↑');
			dfs(row-1, col, map);
		}
		if(!find) {
			steps.add('←');
			dfs(row, col-1, map);
		}
	}

}
