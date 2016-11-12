package test;

import factory.ApplicationContext;
import factory.ClassPathXmlApplicationContext;
import resource.LocalFileResource;

public class test {

    public static void main(String[] args) {
    	LocalFileResource locations = new LocalFileResource ("bean.xml");
      ApplicationContext ctx = 
		    new ClassPathXmlApplicationContext(locations);
        boss boss = (boss) ctx.getBean("boss");
        boss.tostring();
    }
}