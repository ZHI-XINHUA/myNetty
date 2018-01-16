package zxh.netty.protocol;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


import zxh.netty.protocol.coder.NettyMessageDecoder;
import zxh.netty.protocol.coder.NettyMessageEncoder;
import zxh.netty.protocol.handler.HeartBeatReqHandler;
import zxh.netty.protocol.handler.LoginAuthReqHandler;

public class ProtocalClient {
	
	 private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
	    EventLoopGroup group = new NioEventLoopGroup();

	    public void connect(int port, String host,final int localPort) throws Exception {
	        try {
	            Bootstrap b = new Bootstrap();
	            b.group(group).channel(NioSocketChannel.class)
	                    .option(ChannelOption.TCP_NODELAY, true)
	                    .handler(new ChannelInitializer<SocketChannel>() {
	                        @Override
	                        protected void initChannel(SocketChannel ch) throws Exception {
	                            //ch.pipeline().addLast(MarshallingFactory.buildMarshallingDecoder());

	                            ch.pipeline().addLast(new NettyMessageDecoder(1024*1024, 4, 4,-8,0));
	                            ch.pipeline().addLast("MessageEncoder", new NettyMessageEncoder());

	                            //ch.pipeline().addLast(MarshallingFactory.buildMarshallingEncoder());
	                            ch.pipeline().addLast("LoginAuthHandler", new LoginAuthReqHandler());
	                            ch.pipeline().addLast("HeartBeatHandler", new HeartBeatReqHandler());
	                        }
	                    });
	            //发起异步操作
	            ChannelFuture future = b.connect(new InetSocketAddress(host, port),
	                    new InetSocketAddress(ProtocalConstant.LOCALIP, localPort)).sync();
	            future.channel().closeFuture().sync();
	        } finally {
	            //断线重连
	            executorService.execute(new Runnable() {
	                public void run() {
	                    try {
	                        TimeUnit.SECONDS.sleep(5);
	                        try {
	                            connect(ProtocalConstant.PORT, ProtocalConstant.REMOTEIP,localPort);
	                        } catch (Exception e) {
	                            e.printStackTrace();
	                        }
	                    } catch (Exception e) {
	                        e.printStackTrace();
	                    }
	                }
	            });
	        }
	    }

	    public static void main(String[] args) {
	        try {
	            new ProtocalClient().connect(ProtocalConstant.PORT, ProtocalConstant.REMOTEIP,7395);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

}
