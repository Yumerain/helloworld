package game.tetris;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

/** 
 * 主窗口
 * @author zhangyu
 */
public class MainWindow extends JFrame {
	
	private static final long serialVersionUID = 5077447958225132515L;

	public static void main(String[] args) {
		// 实例化一个窗口
		MainWindow win = new MainWindow();
		// 设置标题
		win.setTitle("这是一个java窗口");
		// 关闭窗口时，终止程序
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 游戏逻辑流程实现
		Game game = new Game();
		// 游戏显示面板
		Panel panel = new Panel(game);
		win.add(panel);
		panel.setPreferredSize(new Dimension(game.getWidth(), game.getHeight()));
		// 适配大小
		win.pack();
		// 取得屏幕大小
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
		int width = (int)(screenSize.getWidth()-win.getWidth())/2;
		int height = (int)(screenSize.getHeight()-win.getHeight())/2;
		
		win.setLocation(width,height);		// 设置窗口位置于屏幕正中央
		panel.startUpdate();						// 启动面板绘图刷新
		game.start();									// 启动游戏进度刷新
		win.setVisible(true);						// 设置窗口可见
		win.addKeyListener(game);				// 窗口添加键监听事件，开始接受用户控制(Game类实现了KeyListener接口)
	}
	
}
