package zxh.netty.ssl.oneway;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class SecureChatServer {
	
	public void run(int port) throws InterruptedException{
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workGroup = new NioEventLoopGroup();
		try{
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workGroup)
			.channel(NioServerSocketChannel.class)
			.handler(new LoggingHandler(LogLevel.INFO))
			.childHandler(new SecureChatServerInitializer());
			
			ChannelFuture cf = b.bind(port).sync();
			
			cf.channel().closeFuture().sync();
			
		}finally{
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
		}
		
	}

	public static void main(String[] args) throws InterruptedException {
		new SecureChatServer().run(8765);

	}

}
