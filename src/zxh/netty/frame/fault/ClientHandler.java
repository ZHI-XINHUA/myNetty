package zxh.netty.frame.fault;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class ClientHandler extends ChannelHandlerAdapter {
	
	 private int counter;
	 private byte[] req;
	 
	 public ClientHandler() {
			req = ("QUERY TIME ORDER" + System.getProperty("line.separator")).getBytes();
	 }

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ByteBuf message = null;
		for (int i = 0; i < 100; i++) {
		    message = Unpooled.buffer(req.length);
		    message.writeBytes(req);
		    ctx.writeAndFlush(message);
		}
		
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		ByteBuf buf = (ByteBuf)msg;
		byte[] resp = new byte[buf.readableBytes()];
		buf.readBytes(resp);
		String body = new String(resp,"utf-8");
		System.out.println("Now is : " + body + " ; the counter is : "+ ++counter);
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
