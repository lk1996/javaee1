package MVC;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ModelAndView {
	String viewname = "";
	Map<String, Object> attributeMap = new HashMap<String, Object>();
    ArrayList<atrObject> objectMap = new ArrayList<atrObject>();
	public ModelAndView() {
	}

	public void setAttribute(String name, Object value) {
		attributeMap.put(name, value);
	}

	public void setViewName(String ViewName) {
		viewname = ViewName;
		
	}
    public String getViewName(){
    	return viewname;
    }
	public Object getMap(String string) {
		return attributeMap.get(string);
	}

	public void addObject(String name, Object value) {
		atrObject object = new atrObject();
		object.setName(name);
		object.setValue(value);
		objectMap.add(object);
	}
	
	public ArrayList<atrObject> getObjectMap(){
		return objectMap;
		
	}

}
