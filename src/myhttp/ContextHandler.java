package myhttp;

/**
 * 请求处理器 
 * @author zhangyu
 */
public abstract class ContextHandler {
	
	/** 默认的字符集 */
	protected final String defaultCharset = ClientProcessor.CHARSET;
	
	private String context;
	
	/**
	 * 使用需要处理的uri路径构造，不接受任何形式的URL传参，所有数据以POS提交。
	 * @param uri 必须以 / 开头，指定完整路径
	 */
	public ContextHandler(String uri){
		this.context = uri;
	}
	
	public String getContext(){
		return context;
	}
	
	/** 处理GET */
	public abstract byte[] doGet();
	
	/** 处理POST */
	public abstract byte[] doPost(byte[] bytes);
	
}
