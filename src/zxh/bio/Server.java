package zxh.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
* 客户端
 */
public class Server {

	final static int PROT = 8765;//端口
	
	public static void main(String[] args) {
		ServerSocket serverSocker = null;
		try {
			serverSocker = new ServerSocket(PROT);//监听端口
			System.out.println("Server 启动.......");
			//进行阻塞(获取客户端socket)
			Socket socket = serverSocker.accept();
			
			//新建一个线程执行客户端的任务（每一个客户端，服务端都会新建一个线程处理）
			new Thread(new ServerHandler(socket)).start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(serverSocker!=null){
				try {
					serverSocker.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}

	}

}
