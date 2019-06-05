package cn.edustar.jitar.ex;

import cn.edustar.jitar.JitarException;

/**
 * 分类服务的异常。
 * 
 *
 *
 */
public class CategoryException extends JitarException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2188118278065216726L;

	/**
	 * 构造。
	 */
	public CategoryException() {
		
	}
	
	/**
	 * 构造。
	 * @param msg
	 */
	public CategoryException(String msg) {
		super(msg);
	}
	
	/**
	 * 构造。
	 * @param msg
	 * @param cause
	 */
	public CategoryException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
	/**
	 * 构造。
	 * @param cause
	 */
	public CategoryException(Throwable cause) {
		super(cause);
	}
}
