package zxh.netty.frame.fixedLen;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class ServerHandler extends ChannelHandlerAdapter {

	int counter = 0;
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("=== Server channel active ===");
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		String body = (String) msg;
		System.out.println("Server: This is"+ ++counter +" times receive client : ["+ body + "]");
	
		//影响信息
		String repMsg = "Hi Client. I'm Server. Welcome to Netty. $_";
		ctx.writeAndFlush(repMsg.getBytes());
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
