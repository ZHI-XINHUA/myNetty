package zxh.netty.frame.fault;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class ServerHandler extends ChannelHandlerAdapter{
	
	private int counter;

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("====== Server channel active =========");
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		ByteBuf buf = (ByteBuf)msg;
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		String body = new String(req,"utf-8");
		body = body.substring(0, req.length- System.getProperty("line.separator").length());
		System.out.println("The time server receive order : " + body
				+ " ; the counter is : " + ++counter);
		
		
		String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new java.util.Date(
				System.currentTimeMillis()).toString() : "BAD ORDER";
			currentTime = currentTime + System.getProperty("line.separator");
		ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
		ctx.writeAndFlush(resp);
		
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
