package zxh.aio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;


public class Client implements Runnable{
	private String address = "127.0.0.1";
	private int port = 8765;
	//客户端通道
	private AsynchronousSocketChannel asc;
	
	public Client() throws IOException{
		asc = AsynchronousSocketChannel.open();
	}
	
	public void connect(){
		asc.connect(new InetSocketAddress(address, port));
	}
	
	private void write(String requestMsg){
		try {
			asc.write(ByteBuffer.wrap(requestMsg.getBytes())).get();
			read();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	private void read() {
		ByteBuffer buf = ByteBuffer.allocate(1024);
		try {
			asc.read(buf).get();
			buf.flip();
			byte[] respByte = new byte[buf.remaining()];
			buf.get(respByte);
			System.out.println(new String(respByte,"utf-8").trim());
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public void run(){
		for(;;);
	}
	
	public static void main(String[] args) throws Exception{
		Client c1 = new Client();
		c1.connect();
		
		Client c2 = new Client();
		c2.connect();
		
		Client c3 = new Client();
		c3.connect();
		
		new Thread(c1, "c1").start();
		new Thread(c2, "c2").start();
		new Thread(c3, "c3").start();
		
		Thread.sleep(1000);
		
		c1.write("c1 aaa");
		c2.write("c2 bbbb");
		c3.write("c3 ccccc");
	}

}
