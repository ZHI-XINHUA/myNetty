package zxh.netty.protocol.handler;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import zxh.netty.protocol.model.Header;
import zxh.netty.protocol.model.MessageType;
import zxh.netty.protocol.model.NettyMessage;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
* @Description: 安全认证
 */
public class LoginAuthRespHandler extends ChannelHandlerAdapter {
	
	 private Map<String, Boolean> nodeCheck = new ConcurrentHashMap<String, Boolean>();
	    private String[] whiteList = {"127.0.0.1", "192.168.1.104"};

	    @Override
	    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
	        NettyMessage message = (NettyMessage) msg;

	        //如果是握手请求消息，处理，其他消息透传
	        if (message.getHeader() != null && message.getHeader().getType() == MessageType.LOGIN_REQ.value()) {
	            String nodeIndex = ctx.channel().remoteAddress().toString();
	            NettyMessage loginResp = null;
	            //重复登录，拒绝
	            if (nodeCheck.containsKey(nodeIndex)) {
	                loginResp = buildResponse((byte) -1);
	            } else {
	                InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
	                String ip = address.getAddress().getHostAddress();
	                boolean isOK = false;
	                for (String WIP : whiteList) {
	                    if (WIP.equals(ip)) {
	                        isOK = true;
	                        break;
	                    }
	                }
	                loginResp = isOK ? buildResponse((byte) 0) : buildResponse((byte) -1);
	                if (isOK)
	                    nodeCheck.put(nodeIndex, true);
	            }
	            System.out.println("The login response is : " + loginResp + " body [" + loginResp.getBody() + "]");
	            ctx.writeAndFlush(loginResp);
	        } else {
	            ctx.fireChannelRead(msg);
	        }
	    }

	    private NettyMessage buildResponse(byte b) {
	        NettyMessage msg = new NettyMessage();
	        Header header = new Header();
	        header.setType(MessageType.LOGIN_RESP.value());
	        msg.setHeader(header);
	        msg.setBody(b);
	        return msg;
	    }

	    @Override
	    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
	        nodeCheck.remove(ctx.channel().remoteAddress().toString());
	        ctx.close();
	        ctx.fireExceptionCaught(cause);
	    }
}
