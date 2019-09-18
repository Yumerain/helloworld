package minijava;


/**
 * IWritable 支持 OutputStream、Writer 双模式动态切换输出
 * 
 * 详见 com.jfinal.template.stat.ast.Text 中的用法
 */
public interface IWritable {
	
	/**
	 * 供 OutputStream 模式下的 ByteWrite 使用
	 */
	public byte[] getBytes();
	
	/**
	 * 供 Writer 模式下的 CharWrite 使用
	 */
	public char[] getChars();
}



