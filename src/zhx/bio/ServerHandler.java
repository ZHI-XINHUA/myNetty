package zhx.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
* 服务端处理类
 */
public class ServerHandler implements Runnable{
	private Socket socket;
	
	public ServerHandler(Socket socket){
		this.socket = socket;
	}

	@Override
	public void run() {
		BufferedReader in = null;
		PrintWriter out = null;
		try{
			//得到的是一个输入流。服务端的Socket对象上的getInputStream方法得到的输入流其实就是从客户端发送给服务器端的数据流。
			in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
			//得到的是一个输出流，服务端的Socket对象上的getOutputStream方法得到的输出流其实就是发送给客户端的数据。
			out = new PrintWriter(this.socket.getOutputStream(), true);
			
			String msg = "";
			while(true){
				msg = in.readLine();
				if(msg==null) break;
				System.out.println("Server获取 Client信息:" + msg); //输出客户端发送消息
				out.println("我是服务端的数据.");//服务端发送消息给客户端
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(out!=null){
				try {
					out.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(socket !=null){
				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}

	
}
