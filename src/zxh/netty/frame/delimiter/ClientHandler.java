package zxh.netty.frame.delimiter;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class ClientHandler extends ChannelHandlerAdapter {
	 
	private int counter;

	static final String ECHO_REQ = "Hi, Server. I'm Client, I am learning Netty.$_";

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("====Client channel active====");
		for(int i=0;i<10;i++){
			ctx.writeAndFlush(Unpooled.copiedBuffer(ClientHandler.ECHO_REQ.getBytes()));
		}
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		System.out.println("This is " + ++counter + " times receive server : ["
				+ msg + "]");
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

	
}
