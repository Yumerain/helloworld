package game.chess;

public class Player {
	
	public Player(String name){
		// 给玩家取名字
		this.name = name;
		// 玩家初始位置为0
		this.position = 0;
	}
	
	// 玩家的位置
	public int position;
	// 表示玩家移动的位置
	public int step;

	// 玩家的名称
	public String name;
	
	// 表示玩家遭遇的时间：1-普通移动，2-踩到地雷，3-奖励一次掷骰子
	public int event;
	
	// 玩家移动的方法
	public void move(int step, String[] map){
		// 移动后的位置
		int forward = position + step;
		if(forward >= map.length) forward = map.length-1;
		
		if("※".equals(map[forward])){
			position = forward - 3;
			if(position<0) position = 0; // 不能退到起点之后
			event = 2;
		}else if("Ｒ".equals(map[forward])){
			position = forward;
			event = 3;			
		}else if("〉".equals(map[forward])){
			position = forward + 5;
			if(position>=map.length) position = map.length - 1; // 不能超出地图终点
			event = 4;
		}else{
			position = forward;
			event = 1;
		}
		// 记录玩家移动的步数
		this.step = step;
	}
	
	public void show(Player other){
		switch (event) {
		case 1:
			System.out.print("玩家"+name+"前进了" + step + "步，当前位置=" + position + ", 玩家"+other.name+"位置=" + other.position);
			break;
		case 2:
			System.out.print("玩家"+name+"先前进了" + step + "步，又踩到地雷后退3步，当前位置=" + position + ", 玩家"+other.name+"位置=" + other.position);
			break;
		case 3:
			System.out.print("玩家"+name+"前进了" + step + "步，遇到奖励再掷骰子一次，当前位置=" + position + ", 玩家"+other.name+"位置=" + other.position);
			break;
		case 4:
			System.out.print("玩家"+name+"前进了" + step + "步，一帆风顺前进５步，当前位置=" + position + ", 玩家"+other.name+"位置=" + other.position);
			break;	
		}
	}
}
