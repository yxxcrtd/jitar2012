package cn.edustar.jitar;

/**
 * 系统异常类
 * 
 *
 */
public class JitarException extends RuntimeException {

	/** serialVersionUID */
	private static final long serialVersionUID = 4612529202148018447L;

	/**
	 * 构造
	 */
	public JitarException() {
		// 
	}

	/**
	 * 构造
	 * 
	 * @param msg
	 */
	public JitarException(String msg) {
		super(msg);
	}

	/**
	 * 构造
	 * 
	 * @param msg
	 * @param cause
	 */
	public JitarException(String msg, Throwable cause) {
		super(msg, cause);
	}

	/**
	 * 构造
	 * 
	 * @param cause
	 */
	public JitarException(Throwable cause) {
		super(cause);
	}

}
