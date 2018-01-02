package zhx.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class Server implements Runnable{
	//多路复用器（管理通道）
	private Selector selector;
	//建立读缓冲区
	private ByteBuffer readBuffer = ByteBuffer.allocate(1024);
	//建立写缓冲区
	private ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
	
	public Server(int port){
		try {
			//1、打开多路复用器
			this.selector = Selector.open();
			//2 打开服务器通道
			ServerSocketChannel ssc = ServerSocketChannel.open();
			//3 设置服务器通道为非阻塞模式
			ssc.configureBlocking(false);
			//4 绑定地址
			ssc.bind(new InetSocketAddress(port));
			//5 把服务器通道注册到多路复用器上，并且监听阻塞事件
			ssc.register(this.selector, SelectionKey.OP_ACCEPT);
			
			System.out.println("Server start, port :" + port);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run(){
		for(;;){
			try {
				//1、让多复用器开始监听
				this.selector.select();
				//2 返回多路复用器已经选择的结果集
				Iterator<SelectionKey> keys = this.selector.selectedKeys().iterator();
				//3、遍历
				while(keys.hasNext()){
					//4 获取一个选择的元素
					SelectionKey key = keys.next();
					//5 直接从容器中移除就可以了
					keys.remove();
					//6 如果是有效的
					if(key.isValid()){
						//如果状态为阻塞
						if(key.isAcceptable()){
							accept(key);
						}
						
						//如果状态为可读
						if(key.isReadable()){
							read(key);
						}
						
						//如果状态为可写
						if(key.isWritable()){
							
						}
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	private void read(SelectionKey key){
		try {
			//1、情况缓冲区旧数据
			this.readBuffer.clear();
			//2 获取之前注册的socket通道对象(客户端通道)
			SocketChannel sc = (SocketChannel)key.channel();
			//3 读取数据
			int count = sc.read(this.readBuffer);
			//4 如果没有数据
			if(count==-1){
				key.channel().close();
				key.cancel();
				return ;
			}
			//5 有数据则进行读取 读取之前需要进行复位方法(把position 和limit进行复位)
			this.readBuffer.flip();
			//6 根据缓冲区的数据长度创建相应大小的byte数组，接收缓冲区的数据
			byte[] bytes = new byte[this.readBuffer.remaining()];
			//7 接收缓冲区数据
			this.readBuffer.get(bytes);
			//8 打印结果
			String body = new String(bytes).trim();
			System.out.println("Server : " + body);
			
			// 9..可以写回给客户端数据 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void accept(SelectionKey key){
		try {
			//1 获取服务通道
			ServerSocketChannel ssc = (ServerSocketChannel)key.channel();
			//2 执行阻塞方法
			SocketChannel sc = ssc.accept();
			//3 设置阻塞模式
			sc.configureBlocking(false);
			//4 注册到多路复用器上，并设置读取标识
			sc.register(this.selector, SelectionKey.OP_READ);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Thread(new Server(9876)).start();
	}

}
