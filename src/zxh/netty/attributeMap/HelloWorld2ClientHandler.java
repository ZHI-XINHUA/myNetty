package zxh.netty.attributeMap;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandlerInvoker;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.EventExecutorGroup;

import java.util.Date;

/**
 * Created by zhixinhua on 18/1/15.
 */
public class HelloWorld2ClientHandler extends ChannelHandlerAdapter {
    public static final AttributeKey<NettyChannel> NETTY_CHANNEL_KEY_2 = AttributeKey.valueOf("netty.channel_2");

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //System.out.println("=====HelloWorld2ClientHandler channelActive======start");
       // Attribute<NettyChannel> attr= ctx.attr(AttributeMapConstant.NETTY_CHANNEL_KEY);
        Attribute<NettyChannel> attr= ctx.attr(NETTY_CHANNEL_KEY_2);
        NettyChannel channel = attr.get();
        if(channel==null){
            NettyChannel newNChannel = new NettyChannel("HelloWorld2Client", new Date());
            attr.setIfAbsent(newNChannel);
        }else{
            System.out.println("channelActive attributeMap 中是有值的");
            System.out.println(channel.getName()+"==="+channel.getCreateDate());

        }
        System.out.println("HelloWorldC2ientHandler Active");
        ctx.fireChannelActive();
        //System.out.println("=====HelloWorld2ClientHandler channelActive======end");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //System.out.println("=====HelloWorld2ClientHandler channelRead======start");
        //Attribute<NettyChannel> attr= ctx.attr(AttributeMapConstant.NETTY_CHANNEL_KEY);
        Attribute<NettyChannel> attr= ctx.attr(NETTY_CHANNEL_KEY_2);
        NettyChannel channel = attr.get();
        if(channel==null){
            NettyChannel newNChannel = new NettyChannel("HelloWorld2Client", new Date());
            attr.setIfAbsent(newNChannel);
        }else{
            System.out.println("channelActive attributeMap 中是有值的");
            System.out.println(channel.getName()+"==="+channel.getCreateDate());

        }

        System.out.println("HelloWorld2ClientHandler read Message:" + msg);

        ctx.fireChannelRead(msg);
        //System.out.println("=====HelloWorld2ClientHandler channelRead======end");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
