package zxh.netty.protocol.handler;

import zxh.netty.protocol.model.Header;
import zxh.netty.protocol.model.MessageType;
import zxh.netty.protocol.model.NettyMessage;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
/**
 * 握手认证
 */
public class LoginAuthReqHandler extends ChannelHandlerAdapter {

	 @Override
	    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
	        NettyMessage message = (NettyMessage) msg;

	        //如果是握手应答消息，需要判断是否认证成功
	        if (message.getHeader() != null && message.getHeader().getType() == MessageType.LOGIN_RESP.value()) {
	            byte loginResult = (Byte) message.getBody();
	            //握手失败，关闭连接
	            if (loginResult != (byte) 0) {
	                ctx.close();
	            } else {
	                System.out.println("Login is ok : " + message);
	                ctx.fireChannelRead(msg);
	            }
	        } else {
	            ctx.fireChannelRead(msg);
	        }
	    }

	    @Override
	    public void channelActive(ChannelHandlerContext ctx) throws Exception {
	        ctx.writeAndFlush(buildLoginReq());
	    }

	    private NettyMessage buildLoginReq() {
	        NettyMessage message = new NettyMessage();
	        Header header = new Header();
	        header.setType(MessageType.LOGIN_REQ.value());
	        message.setHeader(header);
	        return message;
	    }

	    @Override
	    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
	        ctx.fireExceptionCaught(cause);
	    }
}
