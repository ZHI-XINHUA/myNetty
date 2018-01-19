package zxh.netty.ssl.twoway;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class SecureChatClientHandler extends SimpleChannelInboundHandler<String> {

	 @Override
    public void messageReceived(ChannelHandlerContext ctx, String msg) throws Exception {
        System.err.println(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    	cause.printStackTrace();
        ctx.close();
    }

}
