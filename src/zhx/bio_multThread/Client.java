package zhx.bio_multThread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
/**
*客户端
 */
public class Client {
	final static String ADDRESS = "127.0.0.1";
	final static int PORT = 8765;
	
	public static void main(String[] args) throws Exception{
		Socket socket = null;
		BufferedReader in = null;
		PrintWriter out = null;
		for(int i=0;i<50;i++){//模拟多个客户端
			
			try{
					socket = new Socket(ADDRESS, PORT);//建立与服务器端的连接，如果服务器没启动，报Connection refused异常
					//可以得到一个输入流，客户端的Socket对象上的getInputStream方法得到输入流其实就是从服务器端发回的数据。
					in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					//得到的是一个输出流，客户端的Socket对象上的getOutputStream方法得到的输出流其实就是发送给服务器端的数据。服务器端上的使用
					out = new PrintWriter(socket.getOutputStream(), true);
					
					//向服务器端发送信息
					out.println("我是客户端信息"+i);
					
					//获取服务器响应信息
					String response = in.readLine();
					System.out.println("Client获取Server响应信息: " + response);
				
			}catch(Exception e){
				e.printStackTrace();
			}finally{
				if(in != null){
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if(out != null){
					try {
						out.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				if(socket != null){
					try {
						socket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				socket = null;
			}
			
			Thread.sleep(1000);
		}
		

	}


}
