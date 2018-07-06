package game.snake;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;

public class AiBfs {
	// 地图结构
	private byte[][] struct;
	// 需要搜索的目标坐标
	private int tX, tY;
	// 保存访问过的节点左边
	private ArrayDeque<int[]> pointers;
	
	/** 构造函数 */
	public AiBfs(int r, int c, int tX, int tY){
		struct = new byte[r][c];
		pointers = new ArrayDeque<>(r * c);
		struct[tX][tY] = 2;	// 标记水果
		this.tX = tX;
		this.tY = tY;
	}
	
	// 使用指定的左边搜索未访问过的临近坐标
	private int[][] seek(int x, int y){
		int[] u = new int[]{x, y - 1};		// 上
		int[] d = new int[]{x, y + 1};	// 下
		int[] l = new int[]{x - 1, y};		// 左
		int[] r = new int[]{x + 1, y};	// 右
		return new int[][]{u, d, l, r};
	}

	/** 思考下一步怎么走 */
	public void think(Snake snake){
		Block header = snake.getHeader();
		int[][] udlr = seek(header.getColIndex(), header.getRowIndex());
	}
	
	public static void main(String[] args) {
		AiBfs ab = new AiBfs(6, 10, 3, 5);
		for (int i = 0; i < ab.struct.length; i++) {
			for (int k = 0; k < ab.struct[i].length; k++) {
				System.out.print(ab.struct[i][k]);
				System.out.print(",");
			}
			System.out.println();
		}
		
	}
}
