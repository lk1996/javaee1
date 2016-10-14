package XML;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ReadXmlPath {
    /**
     * @param args
     */
    public static void main(String[] args) {
        
        SAXReader reader=new SAXReader();
        try {
            Document doc= reader.read("web.xml");
            
            List<Element> list=doc.selectNodes("/web-app/servlet");
            for (Element element : list) {
                System.out.println(element.getName()+"--"+element.getText());
            }
            
        } catch (DocumentException e) {
            e.printStackTrace();
        }

    }

}