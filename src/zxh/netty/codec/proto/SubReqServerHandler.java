package zxh.netty.codec.proto;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by zhixinhua on 18/1/8.
 */
public class SubReqServerHandler extends ChannelHandlerAdapter{


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Server channel active");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SubscribeReqProto.SubscribeReq req = (SubscribeReqProto.SubscribeReq) msg;
        if("zxh".equalsIgnoreCase(req.getUserName())){
            System.out.println("Server accept client subscribe req :["+req.toString()+"]");
            ctx.writeAndFlush(resp(req.getSubReqId()));
        }

    }

    private SubscribeRespProto.SubscribeReq resp(int subReqId){
        SubscribeRespProto.SubscribeReq.Builder builder = SubscribeRespProto.SubscribeReq.newBuilder();
        builder.setSubRespId(subReqId);
        builder.setRespCode(100);
        builder.setDesc("to client");
        return builder.build();
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
