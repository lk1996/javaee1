package engine;

/**
 * 转换过程异常类
 * 
 * @author Jerome
 *
 */
public class ConvertorException extends Exception {

	/**
	 * code = 0, 配对错误
	 */
	public int code;

	public ConvertorException(int code) {
		this.code = code;
	}

}
