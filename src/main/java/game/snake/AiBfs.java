package game.snake;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * BFS是一种盲目搜寻法，目的是系统地展开并检查图中的所有节点，以找寻结果。
 * 换句话说，它并不考虑结果的可能位址，彻底地搜索整张图，直到找到结果为止它的步骤如下:

- 首先将根节点放入队列中。

- 从队列中取出第一个节点，并检验它是否为目标。

- 如果找到目标，则结束搜寻并回传结果。

- 否则将它所有尚未检验过的直接子节点（邻节点）加入队列中。

- 若队列为空，表示整张图都检查过了——亦即图中没有欲搜寻的目标。结束搜寻并回传“找不到目标”。

缺陷:
1、效率底下， 时间复杂度是:T(n) = O(n^2)

2、每个顶点之间没有权值，无法定义优先级，不能找到最优路线。比如遇到水域需要绕过行走，在宽度算法里面无法涉及。

 * @author rainleaf
 *
 */
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
