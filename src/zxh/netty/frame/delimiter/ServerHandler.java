package zxh.netty.frame.delimiter;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class ServerHandler extends ChannelHandlerAdapter {
	 int counter = 0;

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("====server channel active====");
	}

	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		String body = (String) msg;
		System.out.println("Server: This is"+ ++counter +" times receive client : ["+ body + "]");
	
		//影响信息
		String repMsg = "Hi Client. I'm Server. Welcome to Netty. $_";
		ByteBuf buf = Unpooled.copiedBuffer(repMsg.getBytes());
		ctx.writeAndFlush(buf);
	}

	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		cause.printStackTrace();
		ctx.close();//抛异常关闭资源
		
		
	}
	

}
