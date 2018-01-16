package zxh.netty.protocol;

import zxh.netty.protocol.coder.NettyMessageDecoder;
import zxh.netty.protocol.coder.NettyMessageEncoder;
import zxh.netty.protocol.handler.HeartBeatRespHandler;
import zxh.netty.protocol.handler.LoginAuthRespHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class ProtocolServer {
	
	
	public void bind() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap();

        try {
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 第四个参数是调整因为前2个有关长度的值导致（LengthFieldBasedFrameDecoder里面会把
                            // frameLength与（message的长度加上length有关的偏移量）进行比较，如果不修整会出错
                            ch.pipeline().addLast(new NettyMessageDecoder(1024 * 1024, 4, 4, -8, 0));
                            ch.pipeline().addLast(new NettyMessageEncoder());
                            ch.pipeline().addLast(new LoginAuthRespHandler());
                            ch.pipeline().addLast(new HeartBeatRespHandler());
                        }
                    });
            ChannelFuture f = b.bind(ProtocalConstant.REMOTEIP, ProtocalConstant.PORT).sync();
            System.out.println("Netty server start ok : " + (ProtocalConstant.REMOTEIP + " : " + ProtocalConstant.PORT));
            f.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

	public static void main(String[] args) throws Exception {
		new ProtocolServer().bind();

	}

}
