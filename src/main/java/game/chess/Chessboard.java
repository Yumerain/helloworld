package game.chess;

public class Chessboard {
	
	/**
	 * @param map 游戏地
	 * @param a 玩家位置
	 * @param b 玩家位置
	 */
	public void printChessboard(String[] map, int a, int b){
		
		// 复制一份地图的副本
		String[] mapCopy = new String[map.length];
		System.arraycopy(map, 0, mapCopy, 0, map.length);
		
		// 把地上玩家所在的位置用玩家代号替换
		mapCopy[a] = "Ａ";
		mapCopy[b] = "Ｂ";
		if(a == b) mapCopy[b] = "＃";
		
		// 输出第1行：[□□□□□□□□□□□□□□□□□□□□]
		for (int i = 0; i < 20; i++) {
			System.out.print(mapCopy[i]);
		}
		System.out.println();
		
		// 输出第2、3、4行:[                   □]
		for (int i = 0; i < 3; i++) {
			for (int k = 0; k < 19; k++) {
				System.out.print("　");
			}
			System.out.println(mapCopy[20+i]);
		}
		
		// 输出第5行：[□□□□□□□□□□□□□□□□□□□□]
		for (int i = 19; i >=0; i--) {
			System.out.print(mapCopy[23+i]);	
		}
		System.out.println();
		
		// 输出第6、7、8行：[□                   ]
		for (int i = 0; i < 3; i++) {
			System.out.print(mapCopy[43+i]);
			for (int k = 0; k < 19; k++) {
				System.out.print("　");
			}
			System.out.println();
		}
		
		// 输出第9行：[□□□□□□□□□□□□□□□□□□□□]
		for (int i = 0; i < 20; i++) {
			System.out.print(mapCopy[46+i]);
		}
		System.out.println();
	}

}
