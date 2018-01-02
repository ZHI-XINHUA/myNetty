package zxh.aio;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class ServerCompletionHandler implements CompletionHandler<AsynchronousSocketChannel, Server> {

	@Override
	public void completed(AsynchronousSocketChannel asc, Server attachment) {
		//当有下一个客户端接入的时候 直接调用Server的accept方法，这样反复执行下去，保证多个客户端都可以阻塞
		attachment.assc.accept(attachment, this);
		read(asc);
	}
	
	private void read(final AsynchronousSocketChannel asc){
		//建立读缓存区
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
		asc.read(byteBuffer, byteBuffer, new CompletionHandler<Integer, ByteBuffer>() {

			@Override
			public void completed(Integer resultSize, ByteBuffer attachment) {
				//进行读取之后,重置标识位
				attachment.flip();
				//获得读取的字节数
				System.out.println("Server -> " + "收到客户端的数据长度为:" + resultSize);
				//获取读取的数据
				String resultData = new String(attachment.array()).trim();
				System.out.println("Server -> " + "收到客户端的数据信息为:" + resultData);
				String response = "服务器响应, 收到了客户端发来的数据: " + resultData;
				write(asc, response);
				
			}

			@Override
			public void failed(Throwable e, ByteBuffer attachment) {
				e.printStackTrace();
			}
			
		});
	}
	
	private void write(final AsynchronousSocketChannel asc,String responseMsg){
		//1、建立写缓存区
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
		//2、写入数据
		byteBuffer.put(responseMsg.getBytes());
		//3、复位
		byteBuffer.flip();
		//4、写入管道
		asc.write(byteBuffer);
		
	}

	@Override
	public void failed(Throwable e, Server server) {
		System.out.println("======failed=====");
		e.printStackTrace();
		
	}

}
