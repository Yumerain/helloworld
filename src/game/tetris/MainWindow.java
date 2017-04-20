package game.tetris;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

/** 主窗口 */
public class MainWindow extends JFrame {
	
	private static final long serialVersionUID = 5077447958225132515L;

	public static void main(String[] args) {
		// 实例化一个窗口
		MainWindow win = new MainWindow();
		// 设置标题
		win.setTitle("这是一个java窗口");
		// 关闭窗口时，终止程序
		win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 游戏
		Game game = new Game();
		// 面板
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
		// 设置窗口位置 于 屏幕正中央
		win.setLocation(width,height);
		// 窗口添加键监听事件
		win.addKeyListener(game.createKeyListener());
		// 启动面板刷新
		panel.startUpdate();
		// 启动游戏刷新
		game.start();
		// 设置窗口可见
		win.setVisible(true);
	}
	
}
