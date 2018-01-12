package zxh.netty.file;

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
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

public class FileServer {
	
	public void run(int port){
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workGroup = new NioEventLoopGroup();
		try{
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workGroup)
			.channel(NioServerSocketChannel.class)
			.option(ChannelOption.SO_BACKLOG, 100)
			.childHandler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel sc) throws Exception {
					sc.pipeline().addLast(
							new StringEncoder(CharsetUtil.UTF_8),
							new LineBasedFrameDecoder(1024),
							new StringDecoder(CharsetUtil.UTF_8),
							new FileServerHandler());
				}
			});
			
		ChannelFuture f = b.bind(port).sync();
	    System.out.println("Start file server at port : " + port);
	    f.channel().closeFuture().sync();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			 // 优雅停机
		    bossGroup.shutdownGracefully();
		    workGroup.shutdownGracefully();
		}
	}

	public static void main(String[] args) {
		int port = 8765;
		if (args.length > 0) {
		    try {
			port = Integer.parseInt(args[0]);
		    } catch (NumberFormatException e) {
			e.printStackTrace();
		    }
		}
		
		new FileServer().run(port);

	}

}
