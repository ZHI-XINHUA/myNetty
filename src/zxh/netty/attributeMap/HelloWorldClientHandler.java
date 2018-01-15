package zxh.netty.attributeMap;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandlerInvoker;
import io.netty.util.Attribute;
import io.netty.util.concurrent.EventExecutorGroup;

import java.util.Date;

/**
 * Created by zhixinhua on 18/1/15.
 */
public class HelloWorldClientHandler extends ChannelHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
       // System.out.println("=====HelloWorldClientHandler channelActive======start");
        Attribute<NettyChannel> attr= ctx.attr(AttributeMapConstant.NETTY_CHANNEL_KEY);
        NettyChannel channel = attr.get();
        if(channel==null){
            NettyChannel newNChannel = new NettyChannel("HelloWorld0Client", new Date());
            attr.setIfAbsent(newNChannel);
        }else{
            System.out.println("channelActive attributeMap 中是有值的");
            System.out.println(channel.getName()+"==="+channel.getCreateDate());

        }
        System.out.println("HelloWorldC0ientHandler Active");
        ctx.fireChannelActive();
       // System.out.println("=====HelloWorldClientHandler channelActive======end");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
       // System.out.println("=====HelloWorldClientHandler channelRead======start");
        Attribute<NettyChannel> attr= ctx.attr(AttributeMapConstant.NETTY_CHANNEL_KEY);
        NettyChannel channel = attr.get();
        if(channel==null){
            NettyChannel newNChannel = new NettyChannel("HelloWorld0Client", new Date());
            attr.setIfAbsent(newNChannel);
        }else{
            System.out.println("channelActive attributeMap 中是有值的");
            System.out.println(channel.getName()+"==="+channel.getCreateDate());

        }

        System.out.println("HelloWorldClientHandler read Message:" + msg);

        ctx.fireChannelRead(msg);
        //System.out.println("=====HelloWorldClientHandler channelRead======end");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
