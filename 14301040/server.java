package socket;
import java.net.*;
import java.io.*;

public class server {
	
 public static void main(String args[]){
	 
	 try{
		 ServerSocket server=null;
		 
		 try{
			 
			 server=new ServerSocket(3333);
			 //创建ServerSocket在端口3333监听客户请求
			 
		 }catch(Exception e){
			 System.out.println("can not listen to:"+e);
			 //出错，打印错误信息
		 }
	Socket sok=null;
    while(true){
      try{
		   System.out.println("start");
			sok=server.accept();
			//使用accept()阻塞等待客户请求，有客户请求到来则生成一个Socket对象，并继续执行
			if(sok!=null){
				threadC threa=new threadC(sok);
			    threa.start();
			    sok=null;
			}
				
		 }catch(Exception e){
			 System.out.println("Error"+e);
		 }
		 
		 }
	 
 }catch(Exception e){
	 System.out.println("Error"+e);
	 //出错，打印错误信息
 }
 }


}

class threadC extends Thread{
	
	Socket socket=null;
	public threadC(Socket st){
		socket=st;
	}
	
	
	public void run(){
		
		try{
			 String line;
			 BufferedReader is =new BufferedReader(new InputStreamReader(socket.getInputStream()));
			 //由Socket对象得到输入流，并构造相应的BufferedReader对象
			 
			 PrintWriter os=new PrintWriter(socket.getOutputStream());
			 //由Socket对象得到输出流，并构造PrintWriter对象
			
			 line=is.readLine();
			 String linef="";
			 
			 char[] arry=line.toCharArray();
			 for(int i=arry.length-1;i>0;i--){
				 linef+=arry[i];
			 }
			 
			 os.println(linef);
			 os.flush();
				
			 
			 os.close();//关闭Socket输出流
			 is.close();//关闭Socket输入流
				
		}catch(IOException e){
					
				}
	}
}

















