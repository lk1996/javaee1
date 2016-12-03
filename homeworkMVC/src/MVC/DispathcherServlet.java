package MVC;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DispathcherServlet extends HttpServlet {

	ModelAndView mdv;
	ModelAndView recv;
	ArrayList<atrObject> list=null;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 覆盖doGet方法
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		doPost(request, response);
	}
	// 覆盖doPost方法
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		mdv = new ModelAndView();
		Object name_value = request.getParameter("name");
		Object pwd = request.getParameter("pas");

		if ((name_value != null && !name_value.equals("")) && (pwd != null && !pwd.equals(""))) {
			mdv.setAttribute("name", name_value);
			mdv.setAttribute("pas", pwd);
			DoController(request.getServletPath());
			
			list = recv.getObjectMap();
			for(int i=0;i<list.size();i++){
				request.setAttribute(list.get(i).getName(), list.get(i).getValue());
			}
			request.getRequestDispatcher(recv.getViewName()+".jsp").forward(request, response);
		}
	}

	private void DoController(String url) {
		String pkg = "test";
		File rt = null;

		try {
			rt = new File("E:\\软件\\新建文件夹\\java\\homeworkMVC\\src\\test");
			loadClassFile(rt, pkg, url);
		} catch (Exception e) {
			System.out.println("扫描test包内java文件出错");
		}

	}

	private void loadClassFile(File root, String packageName, String url) throws Exception {
		File[] childfiles = root.listFiles();
	
		for (int i = 0; i < childfiles.length; i++) {
			File file = childfiles[i];
			if (file.isDirectory()) {
				loadClassFile(file, packageName + file.getName() + ".", url);
			} else {
				String filename = file.getName();

				String name = filename.substring(0, filename.length() - 5);
				Class<?> obj = Class.forName(packageName + "." + name);

				if (obj.getAnnotation(Controller.class) != null) {
					recv = (ModelAndView) DoRequestMapping(obj, url);
				}
			}
		}
	}

	public Object DoRequestMapping(Class<?> obj, String url) {
		Method[] method = obj.getMethods();
		Object ob = null;
		for (Method m : method) {
			if (m.getAnnotation(RequestMapping.class)!=null) {
				if(m.getAnnotation(RequestMapping.class).value().equals(url)){
					Object[] object = new Object[1];
					object[0] = mdv;
					try {
						ob = m.invoke(obj.newInstance(), object);
					} catch (Exception e) {
						System.out.println("处理RequestMapping注解出错");
					}
				}
				
			}
		}
		return ob;
	}
}
