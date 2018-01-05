package zxh.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Client {

	public static void main(String[] args) {
		//创建连接的地址
		InetSocketAddress  address = new InetSocketAddress(9876);
		//声明连接通道
		SocketChannel sc = null;
		//建立缓冲区
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
		try{
			//打开通道
			sc = SocketChannel.open();
			//进行连接
			sc.connect(address);
			
			for(;;){
				//定义一个字节数组，然后使用系统录入功能：
				byte[] bytes = new byte[1024];
				System.in.read(bytes);
				
				//把数据放到缓冲区中
				byteBuffer.put(bytes);
				//对缓冲区进行复位
				byteBuffer.flip();
				//通道写入数据
				sc.write(byteBuffer);
				//清空缓冲区数据
				byteBuffer.clear();
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(sc!=null){
				try {
					sc.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		

	}

}
