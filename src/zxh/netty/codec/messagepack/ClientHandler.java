package zxh.netty.codec.messagepack;

import java.util.Random;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by zhixinhua on 18/1/7.
 */
public class ClientHandler extends ChannelHandlerAdapter {


	 @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client channel active");
        for (int i=0;i<10;i++){
            ctx.write(subReq(i));
        }
        ctx.flush();
    }

    private UserInfo subReq(int i) {
    	 Random random = new Random();
    	UserInfo u = new UserInfo();
    	u.setAge(random.nextInt(100));
    	u.setName("server_"+i);
        return u;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Receive server response : [" + msg + "]");
        ctx.write(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
