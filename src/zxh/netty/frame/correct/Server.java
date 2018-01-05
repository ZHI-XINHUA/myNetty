package zxh.netty.frame.correct;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class Server {
	public void bind(int port){
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workGroup = new NioEventLoopGroup();
		try{
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workGroup)
			.channel(NioServerSocketChannel.class)
			.option(ChannelOption.SO_BACKLOG, 1024)
			.option(ChannelOption.SO_SNDBUF, 32*1024)
			.option(ChannelOption.SO_RCVBUF, 32*1024)
			.option(ChannelOption.SO_KEEPALIVE, true)
			.handler(new LoggingHandler(LogLevel.INFO))
			.childHandler(new ChildChannelHandler());
			
			ChannelFuture cf = b.bind(port).sync();
			cf.channel().closeFuture().sync();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
			
		}
	}
	
	private class ChildChannelHandler extends ChannelInitializer<SocketChannel>{

		@Override
		protected void initChannel(SocketChannel sc) throws Exception {
			sc.pipeline().addLast(new LineBasedFrameDecoder(1024));
			sc.pipeline().addLast(new StringDecoder());
			sc.pipeline().addLast(new ServerHandler());
			
		}
		
	}

	public static void main(String[] args) {
		int port = 8765;
		if (args != null && args.length > 0) {
		    try {
			port = Integer.valueOf(args[0]);
		    } catch (NumberFormatException e) {
			// 采用默认值
		    }
		}
		
		new Server().bind(port);

	}

}
