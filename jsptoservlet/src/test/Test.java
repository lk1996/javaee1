package test;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import engine.ConvertorException;
import engine.JspToJavaConvertor;
import tools.MyFileReader;

/**
 * 测试类
 * 
 * @author Jerome
 *
 */
public class Test {

	private final static String INPUTPATH = "jsp/";
	private final static String OUTPUTPATH = "output/";

	public static void main(String[] args) {
		String filename = "index.jsp";
		Test test = new Test();
		test.start(filename);
	}

	private void start(String filename) {
		// 获取文件名
		int index = filename.indexOf(".jsp");
		if (index == -1) {
			System.out.println("不是jsp文件！ ");
			return;
		}
		String fName = filename.substring(0, index);
		/* 读取文件 */
		String bigStr = "";
		bigStr = MyFileReader.readFromFile(INPUTPATH + filename);
		/* 转换 */
		JspToJavaConvertor convertor = new JspToJavaConvertor(fName);
		String convertedString = "";
		try {
			convertedString = convertor.convert(bigStr);
		} catch (ConvertorException e1) {
			e1.printStackTrace();
			System.exit(0);
		}
		System.out.print("转换后： \n" + convertedString);
		/* 写入文件 */
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(OUTPUTPATH + fName + ".java");
			pw.write(convertedString);
			pw.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			pw.close();
		}
	}

}
