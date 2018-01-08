package zxh.netty.codec.proto;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by zhixinhua on 18/1/8.
 */
public class SubReqClientHandler extends ChannelHandlerAdapter {



    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client channel active");
        for(int i=0;i<10;i++){
            ctx.write(req(i));
        }
        ctx.flush();

    }

    private SubscribeReqProto.SubscribeReq req(int i){
        SubscribeReqProto.SubscribeReq.Builder builder = SubscribeReqProto.SubscribeReq.newBuilder();
        builder.setSubReqId(i);
        builder.setUserName("zxh");
        builder.setProductName("netty book");
        builder.setAddress("beijing");

        return builder.build();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SubscribeRespProto.SubscribeReq reps = (SubscribeRespProto.SubscribeReq)msg;
        System.out.println("Receive server response :["+reps.toString()+"]");
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
