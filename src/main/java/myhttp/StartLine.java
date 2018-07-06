package myhttp;

/**
 * HTTP请求行
 */
public class StartLine {
	
	private static final HttpProtocolException exception = new HttpProtocolException("请求起始行不符合协议");
	
	private static final String V11 = " HTTP/1.1";
	private static final String V10 = " HTTP/1.0";
	
	private String method;
	private String uri;
	private String version;
	
	/**
	 * 跟据请求行字符创建
	 * @param line
	 * @return
	 * @throws HttpProtocolException 
	 */
	public static StartLine parse(String line) throws HttpProtocolException{
		StartLine sl = new StartLine();
		// 在已知的[GET,HEAD,POST,PUT,DELETE,TRACE,OPTIONS,CONNECT]最长方法名中找7个字符+1个空格内查找第一个空格
		int index = -1;	// 第一个空格：为节省时间，最多只在8个符内查找
		for (int i = 0; i < 8; i++) {
			if(line.charAt(i) == ' '){
				index = i;
				break;
			}
		}
		if(index<=0  || index>=8){
			throw exception;	// 请求不符合HTTP协议
		}
		//请求行长度至少>(index+1)+SUFFIX.length()才有空间放URI的内容
		if(line.length() <= index+1+V11.length()){
			throw exception;	// 请求不符合HTTP协议
		}
		// 检测HTTP版本：最后一个空格之后的内容
		if(line.charAt(line.length() - V11.length() - 1)==' '){
			throw exception;	// 请求不符合HTTP协议
		}
		String ver = line.substring(line.length()-V11.length(), line.length());
		if(V11.equals(ver)){
			sl.version = V11;
		} else if(V10.equals(ver)){
			sl.version = V10;
		} else {
			throw exception;	// 请求不符合HTTP协议
		}
		sl.method = line.substring(0, index);
		sl.uri = line.substring(index+1, line.length()-V11.length());
		return sl;
	}
	
	public String getMethod() {
		return method;
	}

	public String getUri() {
		return uri;
	}
	
	public String getVersion(){
		return version;
	}
	
	@Override
	public String toString() {
		return "method:[" + method + "] uri:[" + uri + "] version:[" + version + "]";
	}
	
}

