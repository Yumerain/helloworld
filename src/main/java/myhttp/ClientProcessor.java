package myhttp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


public class ClientProcessor extends Thread {

	private static Logger logger = Logger.getLogger(ClientProcessor.class.toString());
	
	private static HttpProtocolException headExcpt = new HttpProtocolException("请求头部不符合协议");
	
	/** HTTP协议定义的换行符 */
	private static final String CRLF = "\r\n";
	
	/** 请求报头每行最大字符数量 */
	private static final int HEADLINE_MAX = 2048;
	
	/** 请求报头最大个数 */
	private static final int HEADCOUNT_MAX = 100;
	
	/** 服务器编码 */
	public static final String CHARSET = "GBK";
	

	/** 上下文处理器 */
	private Map<String, ContextHandler> contextHandlerMap = new HashMap<String, ContextHandler>();
	
	/** HTTP请求报头 */
	private Map<String, String> headers = new HashMap<String, String>(10);
	
	/** HTTP状态 */
	private int statusCode = 200;
	
	private String uri;
	private String method;
	
	/** POST发送过来的数据 */
	private byte[] content = new byte[0];
	
	/** 启动新线程异步处理 */
	public static void process(Socket socket) {
		Thread thread = new ClientProcessor(socket);
		thread.setName("处理线程id=" + thread.getId());
		thread.start();
	}

	/** 连接的客户端 */
	private Socket socket;

	/**
	 * 构造方法
	 * @param skt
	 */
	public ClientProcessor(Socket skt){
		this.socket = skt;
		// TODO: ADD handler1
		//addContextHandler(new ContextHandler("/url_1"));
		// TODO: ADD handler2
		//addContextHandler(new ContextHandler("/url_2"));
	}
	
	/** 添加上下文处理器 */
	public void addContextHandler(ContextHandler ctx){
		contextHandlerMap.put(ctx.getContext(), ctx);
	}
	
	/**
	 * 执行处理
	 */
	public void run() {
		logger.info("开始处理来自[" + socket.getInetAddress().getHostAddress() + ":" + socket.getPort() + "]客户端请求.");
		try {
			socket.setTcpNoDelay(true);
			socket.setSoTimeout(1 * 60 * 1000);// 设置读取超时：1分钟
			
			// 接受请求
			request(socket.getInputStream());
			
			// 返回响应
			response(socket.getOutputStream());
			
			logger.info("来自[" + socket.getInetAddress().getHostAddress() + ":" + socket.getPort() + "]的请求已响应["+statusCode+"].");
		} catch (Exception e) {
			logger.warning("处理来自[" + socket.getInetAddress().getHostAddress() + ":" + socket.getPort() + "]的客户端socket出错.");
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
			}
		}
	}

	private void request(InputStream input) {
		try {
			// 读取【起始行】，也就HTTP开始的第一行
			String startLine = readHeadLine(input);
			StartLine sl = StartLine.parse(startLine);
			this.uri = sl.getUri();
			this.method = sl.getMethod();
			
			// 读取完所有的header
			readAllHeaders(input);
			
			// 如果是GET请求，则直接结束
			if("GET".equals(method)){
				return;
			}
			// 如果是POST请求，则读取POST数据，同时要求请求报头包含Content-Length
			if("POST".equals(sl.getMethod())){
				if(!headers.containsKey("Content-Length")){
					statusCode = 400;	// POST缺少相关头部 
					return;
				}
				int length = Integer.parseInt(headers.get("Content-Length"));
				if(length <= 0){
					return;
				}
				ByteArrayOutputStream mem = new ByteArrayOutputStream(length);
				while(true){
					int bt = input.read();
					if(bt == -1)break;
					mem.write(bt);
					// 最多只读取Content-Length的长度
					if(mem.size()==length){
						break;
					}
				}
				// 如果POST的数据不符合
				if(mem.size()<length){
					statusCode = 400;
					return;
				}
				// POST的内容
				content = mem.toByteArray();
				return;
			}
			statusCode = 405;	// GET和POS之外的请求暂不处理
		} catch (HttpProtocolException e) {
			statusCode = 400;	// HTTP协议语意错误
			logger.warning(e.getMessage());
		} catch (IOException e) {
			statusCode = 500;	// 服务器内部错误
			logger.warning(e.getMessage());
		} catch (Exception e) {
			statusCode = 500;	// 服务器内部错误
			logger.warning(e.getMessage());
		}
	}
	
	/** 从输入流中读取头部行 */
	private String readHeadLine(InputStream is) throws IOException, HttpProtocolException {
		StringBuilder buff = new StringBuilder(256);		// 通常HTTP协议请求每行200个字符内
		while(true){
			int bt = is.read();
			if(bt == -1)break;
			buff.append((char)bt);
			// 数据长度检查：在请求的http header行内需要限制每行的最大长度，预防非法请求
			if(buff.length()>HEADLINE_MAX){
				logger.warning("请求头部单行数据超过设定最大值：" + buff.toString());
				throw headExcpt;
			}
			// 判断是否出现[CRLF]换行：严格验证必需同时出现
			if(buff.charAt(buff.length()-1)=='\n' && buff.length()>=2 && buff.charAt(buff.length()-2)=='\r'){
				// 读取到CRLF，则一行结束
				break;
			}
		}
		// 删除CRLF
		buff.setLength(buff.length()-2);
		// 返回不带换行的字符
		return buff.toString();
	}
	
	/** 读取所有HTTP报头 */
	private void readAllHeaders(InputStream is) throws IOException, HttpProtocolException {
		int count = HEADCOUNT_MAX;
		while(count>0){
			String line = readHeadLine(is);
			if(line.isEmpty()){
				break;	// 空行说明头部结束了
			}
			int splitIndex = line.indexOf(": ");		// 严格按照一个冒号、一个空格
			if(splitIndex == -1){
				logger.warning("不符合HTTP协议的头部：" + line);
				throw headExcpt;
			}
			headers.put(line.substring(0, splitIndex), line.substring(splitIndex+2));
			count--;
		}
	}

	private void response(OutputStream output) throws IOException {
		if(statusCode != 200){
			notFoundRequest(output);
			return;
		}
		// 寻找对应的上下文处理器
		ContextHandler contextHandler = contextHandlerMap.get(uri);
		if(contextHandler == null){
			notFoundRequest(output);
			return;
		}
		
		byte[] result;
		// 仅仅支持GET与POST
		if("GET".equals(method)){
			result = contextHandler.doGet();
		}else if("POST".equals(method)){
			result = contextHandler.doPost(content);
		}else{
			notFoundRequest(output);
			return;
		}
		StringBuilder buff = new StringBuilder();
		buff.append("HTTP/1.1 200 OK").append(CRLF);
		//Date: Fri, 20 Nov 2015 10:20:47 GMT
		buff.append("Content-Type: text/plain; charset=" + CHARSET).append(CRLF);
		buff.append("Server: Java-Transformers V1.1").append(CRLF);
		buff.append("Connection: close").append(CRLF);
		buff.append("Content-Length: ").append(result.length).append(CRLF);
		buff.append(CRLF);
		// 输出head：head只能是英文字母，对字符集无特别要求
		output.write(buff.toString().getBytes(CHARSET));
		output.flush();
		// 输出内容
		output.write(result);
		output.flush();
	}
	
	/** 不支持的请求的响应:404 Not Found */
	private void notFoundRequest(OutputStream os) throws IOException{
		String notice = "404 Not Found";
		StringBuilder buff = new StringBuilder();
		buff.append("HTTP/1.1 404 Not Found").append(CRLF);
		buff.append("Server: Java-Transformers V1.0").append(CRLF);
		buff.append("Connection: close").append(CRLF);
		buff.append("Content-Type: text/plain").append(CRLF);
		buff.append("Content-Length: ").append(notice.length()).append(CRLF);
		buff.append(CRLF);
		buff.append(notice);
		os.write(buff.toString().getBytes(CHARSET));
		os.flush();
	}
}
