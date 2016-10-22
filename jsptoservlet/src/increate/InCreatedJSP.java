package increate;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 构造内建对象
 * 
 * @author Jerome
 *
 */
public class InCreatedJSP {

	protected PrintWriter pStream;

	protected ServletRequest request;
	protected ServletResponse response;

	public InCreatedJSP(ServletRequest request, ServletResponse response) {
		this.request = request;
		this.response = response;

		try {
			this.pStream = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
