package game.chess;

import java.util.Scanner;

public class Game {
	
	public static void main(String[] args) {
		Game lc = new Game();
		lc.start();
	}
	
	// true为A投骰子，false为B投骰子
	private boolean flag = true;
	// 游戏玩家A
	private Player a = new Player("[乔峰]");
	// 游戏玩B
	private Player b = new Player("[段誉]");

	public void start() {
		// 输入工具
		Scanner sc = new Scanner(System.in);
		
		// 游戏启动提示语
		System.out.println("※※※※※※※※※※※※※※※※※※※");
		System.out.println("※※※※※※※飞行棋※※※※※※※※※");
		System.out.println("※※※※※※※※※※※※※※※※※※※");
		System.out.println("*1.玩家轮流投掷骰子");
		System.out.println("*2根据骰子点数走相应步数");
		System.out.println("*3先到达终点的胜利");
		
		
		// 游戏流程开始
		Chessboard cb = new Chessboard(); // 棋盘
		// 游戏地图
		String[] map = new String[]{
				"◎","１","２","３","Ｒ",    "５","６","７","※","９",
				"10","〉","□","□","□",    "15","□","□","□","□",
				"20","□","□","□","□",    "25","□","□","□","□",
				"30","□","□","□","□",    "35","□","□","＠","□",
				"40","□","□","□","□",    "45","□","□","□","□",
				"50","□","□","□","□",    "55","□","□","□","□",
				"60","□","□","□","64",    "★"
		};
		
		int end = 66;  // 终点位置
		
		// 显示棋盘初始状态
		cb.printChessboard(map, a.position, b.position);
		
		while(a.position<end-1 && b.position<end-1){ // 只要有任何一个玩家到达终点则游戏结束	
			System.out.println("\n\n\n\n\n");
			int step = random();
			
			if(flag){
				System.out.print("请玩家"+a.name+"投骰子（请输入任意字符）：");
				sc.next();
				// 玩家A移动
				a.move(step, map);				
				
				// 根据是否是 奖励投掷事件，决定是否改变标识
				if(a.event != 3){
					flag = false;					
				}				
				
				// 移动结束，显示棋盘
				cb.printChessboard(map, a.position, b.position);				
				// 移动结束，显示位置
				a.show(b);
			}else{
				System.out.print("请玩家"+b.name+"投骰子（请输入任意字符）：");
				sc.next();
				// 玩家B移动
				b.move(step, map);
				
				// 根据是否是 奖励投掷事件，决定是否改变标识
				if(b.event != 3){
					flag = true;				
				}				
				
				// 移动结束，显示棋盘
				cb.printChessboard(map, a.position, b.position);				
				// 移动结束，显示位置
				b.show(a);
			}
		}
		
		// 游戏结束提示胜利。
		if(a.position>b.position){
			System.out.println("恭喜玩家"+a.name+"胜利！");
		}else{
			System.out.println("恭喜玩家"+b.name+"胜利！");
		}
	}
	
	/** 产生一个1~6的数字 */
	public static int random(){		
		int num = 0;
		while(num<1 || num>6){
			double rd = Math.random();
			num = (int)(rd*10);
		}
		return num;		
	}
	
}
