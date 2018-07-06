package myhttp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

/**
 * 清算组.转账通系统专用轻量级HTTP服务器
 * @author zhangyu
 */
public class QszHttpServer extends Thread {

	// 日志工具
	private static Logger logger = Logger.getLogger(QszHttpServer.class.toString());

	/**
	 * 监听本地端口数据
	 * @param port
	 */
	public static void listen(int port){
		try {
			// 启动
			QszHttpServer server = new QszHttpServer(port);
			server.start();
			
			logger.info("HTTP服务已经启动，正始侦听本地["+port+"]端口");
		} catch (Exception e) {
			logger.warning("错误，HTTP侦听本地端口["+port+"]出错，启动失败！");
			System.exit(0);
		}
	}
	
	/** 服务端socket */
	private ServerSocket serverSocket;
	
	/**
	 * 构造方法，侦听一个端口
	 */
	private QszHttpServer (int port) throws IOException{
		// 侦听
		serverSocket = new ServerSocket(port);
	}
	
	/**
	 * 关闭服务器
	 */
	public void shutdown(){
		if(serverSocket == null){
			return;
		}
		try {
			serverSocket.close();
		} catch (Exception e) {
			logger.warning("服务器关闭：" + e.getMessage());
			System.exit(0);
		}
	}

	/**
	 * 开启线程执行
	 */
	public void run() {
		Thread thread = Thread.currentThread();
		thread.setName("HTTP侦听线程id=" + thread.getId());
		try {
			// 检查标记
			while(true){
				// 等待客户端连接
				Socket socket = null;
				try {
					logger.info("正在等待客户端接入……");
					socket = serverSocket.accept();
				} catch (Exception e) {
					logger.warning("等待客户端连接时出错");
					int count = 5;
					while(count>0){
						logger.info(count+"秒后重新侦听……");
						count--;
						try {
							Thread.sleep(1000);
						} catch (Exception ex) {
						}
					}
					continue;
				}
				
				logger.info("来自[" + socket.getInetAddress().getHostAddress() + ":" + socket.getPort() + "]的客户端接入.");
				// 处理客户端连接
				ClientProcessor.process(socket);
			}
		} finally {
			try {
				serverSocket.close();
				logger.info("检测到运行标记锁文件被删除，已关闭HTTP服务器。");
				System.exit(0);
			} catch (IOException e) {
			}
		}
	}
	
}

