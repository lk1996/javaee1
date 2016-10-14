package XML;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
public class Demo {
	private static Map<String, String> servlets = new HashMap<String, String>();
    private static Map<String, String> mappings = new HashMap<String, String>();
	public static void main(String[] args) throws Exception {
		  SAXReader reader = new SAXReader();
		  File file = new File("WebContent\\WEB-INF\\web.xml");
		  Document doc = reader.read(file);
		  Element element = (Element) doc.selectSingleNode("//web-app/servlet");
		  List list = (List) element.elements();
		  for (Object e : list) {
			   Element et = (Element) e;
			   System.out.println("元素名称" + et.getName());
			   System.out.println("元素值" + et.getText());

			  }
//		  for (Element servlet : list) {
//			  System.out.println(111);
//              Element sname = servlet.element("servlet-name");
//              Element sclass = servlet.element("servlet-class");
//              System.out.println(sname.getText()+sclass.getText());
//              servlets.put(sname.getText(), sclass.getText());
//          }
//		  List<Element> list1 = root.elements("/web-app/servlet-mapping");
//		  for (Element servlet : list1) {
//              Element sname = servlet.element("servlet-name");
//              Element url = servlet.element("url-pattern");
//              mappings.put(url.getText(), sname.getText());
//          }
//          String nn="/Login";
//          String aa=mappings.get(nn);
//          String mm=servlets.get(aa);
//          System.out.println(mm);
		 }
}
