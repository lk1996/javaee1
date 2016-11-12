package factory;

import java.io.File;

import java.lang.reflect.Constructor;
import java.util.List;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import bean.BeanDefinition;
import bean.BeanUtil;
import bean.PropertyValue;
import bean.PropertyValues;
import resource.Resource;
import test.Autowired;
import test.Component;

public class ClassPathXmlApplicationContext extends AbstractBeanFactory {

	NodeList beanList = null;

	public ClassPathXmlApplicationContext(Resource resource) {
		DoComponent();

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
			Document document = dbBuilder.parse(resource.getInputStream());
			beanList = document.getElementsByTagName("bean");

			for (int i = 0; i < beanList.getLength(); i++) {
				Node bean = beanList.item(i);
				loadBean(bean.getAttributes().getNamedItem("id").getNodeValue());
			}

		} catch (Exception e) {
			System.out.println("解析xml文件出错！");
		}
	}

	private void loadBean(String BeanName) {

		// 要是还没读入Map则进行load
		if (getBean(BeanName) == null) {
			for (int i = 0; i < beanList.getLength(); i++) {
				Node bean = beanList.item(i);

				String beanClassName = bean.getAttributes().getNamedItem("class").getNodeValue();
				String beanName = bean.getAttributes().getNamedItem("id").getNodeValue();

				if (BeanName.equals(beanName)) {
					BeanDefinition beandef = new BeanDefinition();
					beandef.setBeanClassName(beanClassName);

					try {
						Class<?> beanClass = Class.forName(beanClassName);
						beandef.setBeanClass(beanClass);
					} catch (ClassNotFoundException e) {
						System.out.println("获取class文件出错！");
					}

					PropertyValues propertyValues = new PropertyValues();

					NodeList propertyList = bean.getChildNodes();

					for (int j = 0; j < propertyList.getLength(); j++) {
						Node property = propertyList.item(j);
						if (property instanceof Element) {
							Element ele = (Element) property;

							String name = ele.getAttribute("name");

							if (!ele.getAttribute("value").isEmpty()) {
								try {
									Class<?> type;
									type = beandef.getBeanClass().getDeclaredField(name).getType();
									Object value = ele.getAttribute("value");

									if (type == Integer.class) {
										value = Integer.parseInt((String) value);
									}
									propertyValues.AddPropertyValue(new PropertyValue(name, value));
								} catch (Exception e) {
									System.out.println("");
								}
							}
							if (!ele.getAttribute("ref").isEmpty()) {
								String refBean = ele.getAttribute("ref");

							
								if (getBean(refBean) == null) {
								
									loadBean(refBean);
								}
							}
						}
					}
					beandef.setPropertyValues(propertyValues);

					this.registerBeanDefinition(beanName, beandef);
				}
			}
		}
	}

	public Object DoAutowired(BeanDefinition beandefinition) {
		Class<?> object = beandefinition.getBeanClass();
		Constructor<?>[] cons = object.getConstructors();

		for (Constructor<?> c : cons) {
			if (c.getAnnotation(Autowired.class) != null) {

				Object[] params = new Object[c.getParameterTypes().length];
				for (int i = 0; i < c.getParameterTypes().length; i++) {
					// 得到构造函数中的参数类型
					String par_type = c.getParameterTypes()[i].getName().substring(
							c.getParameterTypes()[i].getName().indexOf('.') + 1,
							c.getParameterTypes()[i].getName().length());
					if (getBean(par_type) != null) {
						params[i] = getBean(par_type);
					}
				}

				try {
					return c.newInstance(params);
				} catch (Exception e) {
					
				}
			}
		}
		return null;
	}

	private void DoComponent() {
		String pkgName = "test";
		File rt = new File(System.getProperty("user.dir") + "\\src\\test");
		try {
			loadClassFile(rt, pkgName);
		} catch (Exception e) {
			
		}
	}

	private void loadClassFile(File root, String packageName) throws Exception {
		File[] childfiles = root.listFiles();
		for (int i = 0; i < childfiles.length; i++) {
			File file = childfiles[i];
			if (file.isDirectory()) {
				loadClassFile(file, packageName + file.getName() + ".");
			} else {
				String filename = file.getName();

				try {
					String name = filename.substring(0, filename.length() - 5);
				
					Class<?> obj = Class.forName(packageName + "."+name);

					if (obj.getAnnotation(Component.class) != null) {
						Component com = (Component)obj.getAnnotation(Component.class);

						BeanDefinition beandef = new BeanDefinition();
						beandef.setBeanClassName(packageName + name);
						beandef.setBeanClass(obj);
						this.registerBeanDefinition(com.value(), beandef);
					}
				} catch (Exception e) {
					
				}
			}
		}
	}

	@Override
	protected BeanDefinition GetCreatedBean(BeanDefinition beanDefinition) {

		try {
			Object bean;
			Class<?> beanClass = beanDefinition.getBeanClass();

		
			if (this.DoAutowired(beanDefinition) != null) {
				bean = this.DoAutowired(beanDefinition);
			} else {
				bean = beanClass.newInstance();
			}

			if (beanDefinition.getPropertyValues() != null) {
				List<PropertyValue> fieldDefinitionList = beanDefinition.getPropertyValues().GetPropertyValues();

				for (PropertyValue propertyValue : fieldDefinitionList) {
					BeanUtil.invokeSetterMethod(bean, propertyValue.getName(), propertyValue.getValue());
				}
			}

			beanDefinition.setBean(bean);

			return beanDefinition;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
