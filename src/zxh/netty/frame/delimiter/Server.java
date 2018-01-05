package zxh.netty.frame.delimiter;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class Server {
	
	public void bind(int port){
		//1、创建线程组
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
	   try{
		 //2、创建工具辅助类，用于服务器管道一系列配置
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
			.channel(NioServerSocketChannel.class)
			.option(ChannelOption.SO_BACKLOG, 1024)
			.option(ChannelOption.SO_SNDBUF, 32*1024)
			.option(ChannelOption.SO_RCVBUF, 32*1024)
			.option(ChannelOption.SO_KEEPALIVE, true)
			.handler(new LoggingHandler(LogLevel.INFO))
			.childHandler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel sc) throws Exception {
					ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
					sc.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));//分隔符
					sc.pipeline().addLast(new StringDecoder());//解码器
					sc.pipeline().addLast(new ServerHandler());//配置接受方法处理
				}
			});
			
			//3、绑定端口，同步等待成功
			ChannelFuture cf =  b.bind(port).sync();
			
			//4、 等待服务端监听端口关闭
			cf.channel().closeFuture().sync();
	   }catch(InterruptedException e){
		   e.printStackTrace();
	   }finally{
		   // 优雅退出，释放线程池资源
		   bossGroup.shutdownGracefully();
		   workerGroup.shutdownGracefully();
	   }
		
		
		
		
	}
	
	public static void main(String[] args){
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
