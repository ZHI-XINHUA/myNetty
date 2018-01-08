package zxh.netty.codec.messagepack;

import zxh.netty.codec.marshalling.MarshallingCodeCFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class Server {
	
	public void bind(int prot){
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workGroup = new NioEventLoopGroup();
		try{
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workGroup)
			.channel(NioServerSocketChannel.class)
			.option(ChannelOption.SO_BACKLOG, 1024)
			.handler(new LoggingHandler(LogLevel.INFO))
			.childHandler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel sc) throws Exception {
					sc.pipeline().addLast("frameDecoder",new LengthFieldBasedFrameDecoder(65535, 0, 8,0,8));
					sc.pipeline().addLast("msgPack Decoder",new MsgPackDecoder());
					sc.pipeline().addLast("frameEncoder",new LengthFieldPrepender(8));
					sc.pipeline().addLast("msgPack Encoder",new MsgPackEncoder());
					sc.pipeline().addLast(new ServerHandler());
					
				}
			});
			
			ChannelFuture cf = b.bind(prot).sync();
			
			cf.channel().closeFuture().sync();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
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
