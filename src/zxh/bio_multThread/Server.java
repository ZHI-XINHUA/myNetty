package zxh.bio_multThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	final static int PORT = 8765;
	
	public static void main(String[] args){
		ServerSocket serverSocker = null;
		
		try{
			serverSocker = new ServerSocket(PORT);//监听端口
			System.out.println("Server 启动");
			
			Socket socket = null;
			HandlerExecutorPool executorPool = new HandlerExecutorPool(50,1000);//自定义线程池，
			while(true){//服务端一直运行
				socket = serverSocker.accept();//进行阻塞(获取客户端socket)
				executorPool.execute(new ServerHandler(socket));//在线程中处理客户端（一个线程处理一个客户端）
			}
			
		}catch(Exception e){
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
