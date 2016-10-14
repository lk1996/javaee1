package XML;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
/**
 * xml 信息读取到 map里
 * 
 * @author Administrator
 * 
 */
public class XmlParse {

    private Map<String, String> servlets = new HashMap<String, String>();
    private Map<String, String> mappings = new HashMap<String, String>();

    public XmlParse() {

        SAXReader reader = new SAXReader();
        try {
            Document doc = reader.read("WebContent//WEB-INF//web.xml");

            List<Element> list = doc.selectNodes("/web-app/servlet");
            for (Element servlet : list) {
                Element sname = servlet.element("servlet-name");
                Element sclass = servlet.element("servlet-class");
                servlets.put(sname.getText(), sclass.getText());
            }

            List<Element> list1 = doc.selectNodes("/web-app/servlet-mapping");
            for (Element servlet : list1) {
                Element sname = servlet.element("servlet-name");
                Element url = servlet.element("url-pattern");
                mappings.put(url.getText(), sname.getText());
            }

        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }

    /**
     * 通过 url 找到 类名
     * 
     * @param url
     * @return
     */
    public String getClassByUrl(String url) {
        String cname = null;

        String sname = mappings.get(url);
        cname = servlets.get(sname);

        return cname;

    }

    public static void main(String[] args) {
        XmlParse xml= new XmlParse();
        String ok=xml.getClassByUrl("/PrimitiveServlet");
        System.out.println(ok);
    }

}