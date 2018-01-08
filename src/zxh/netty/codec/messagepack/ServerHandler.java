package zxh.netty.codec.messagepack;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class ServerHandler extends ChannelHandlerAdapter {


	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("==== Server channel active ====");
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		UserInfo userInfo = (UserInfo)msg;
		String reqName = userInfo.getName();
		
		System.out.println(reqName);
		String[] args = reqName.split("_");
		UserInfo rep = new UserInfo();
		System.out.println("Service accept client request ["+userInfo.toString()+"]");
		rep.setAge(userInfo.getAge());
		rep.setName("to client_"+args[1]);
		ctx.writeAndFlush(rep);
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
