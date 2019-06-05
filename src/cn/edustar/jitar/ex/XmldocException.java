package cn.edustar.jitar.ex;

/**
 * XmlUtil 操作 XML 文档的时候产生的异常。
 * 
 *
 *
 */
public class XmldocException extends RuntimeException {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 6030675158021395010L;

	/**
	 * 构造。
	 */
	public XmldocException() {
		
	}
	
	/**
	 * 构造。
	 */
	public XmldocException(String msg) {
		super(msg);
	}
	
	/**
	 * 构造。
	 */
	public XmldocException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
	/**
	 * 构造。
	 */
	public XmldocException(Throwable cause) {
		super(cause);
	}
}
