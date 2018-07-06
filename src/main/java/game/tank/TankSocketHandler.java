package game.tank;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TankSocketHandler extends Thread {

	private Socket socket;
	private InputStream input;
	private OutputStream output;
	
	public TankSocketHandler(Socket client){
		this.socket = client; 
		InetAddress address = client.getInetAddress();
		System.out.println("客户端接入 IP:" + address.getHostAddress() + " 端口:" + client.getPort() + " 名称:" + address.getHostName());
		try {
			input = client.getInputStream();
			output = client.getOutputStream();
		} catch (IOException e) {
			System.out.println("接入时异常，关闭。");
			try {
				client.close();
			} catch (Exception ex) {
			}
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		try {
			for(;;){
				// 读取报头
				Head head = readHead();
				if("exit".equals(head.name)){
					break;
				}else{
					// 读取数据
					byte[] buff = new byte[head.length];
					System.out.println(new String(buff, "UTF-8"));
				}
			}
			socket.close();
			System.out.println(socket.toString() + "退出");
		} catch (IOException e) {
			System.out.println("接收客户端数据出错");
			e.printStackTrace();
		}	
	}
	
	private Head readHead() throws IOException{
		// 类型		数据长度
		// |-8bytes-|-----16bytes----|
		// |--------|----------------|
		byte[] nameBuff = new byte[8];
		byte[] lengthBuff = new byte[16];
		input.read(nameBuff);
		input.read(lengthBuff);
		
		Head head = new Head();
		head.name = new String(nameBuff).trim();
		head.length = Integer.parseInt(new String(lengthBuff).trim());
		return head;
	}
	
}
