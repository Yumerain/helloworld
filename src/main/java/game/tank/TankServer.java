package game.tank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class TankServer extends Thread{
	
	public static void main(String[] args) {
		TankServer server = new TankServer();
		// 启动服务线程
		server.start();
		// 接受输入
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(System.in, "GBK"));
			String line = null;
			while(!"stop".equals(line=reader.readLine())){
				System.out.println(line);
			}
			System.out.println("stop ……");
			// 关闭
			server.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {reader.close();} catch (Exception ex) {}
		}
	}
	
	private static Logger logger = Logger.getLogger("mainLogger");
	
	private ServerSocket serverSocket;
	
	public TankServer(){
		try {
			serverSocket = new ServerSocket(2345);
		} catch (IOException e) {
			logger.warning("服务器启动失败："+e.getMessage());
			System.exit(1);
		}
	}
	
	public void shutdown() throws IOException{
		serverSocket.close();
	}
	
	public void run(){
		// 只有在未关闭时执行
		while(!serverSocket.isClosed()){
			try {
				logger.info("等待客户端接入……");
				Socket client = serverSocket.accept();
				//new SocketHandler(client).start();
			} catch (IOException e) {
				logger.warning("连接时发生 I/O 错误:"+e.getMessage());
			}
		}
	}

}
