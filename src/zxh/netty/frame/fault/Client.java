package zxh.netty.frame.fault;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Client {
	
	public void connect(String host,int port){
		EventLoopGroup group = new NioEventLoopGroup();
		try{
			Bootstrap b = new Bootstrap();
			b.group(group)
			.channel(NioSocketChannel.class)
			.option(ChannelOption.TCP_NODELAY, true)
			.handler(new ChannelHandler());
			
			ChannelFuture cf = b.connect(host, port).sync();
			
			cf.channel().closeFuture().sync();
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			group.shutdownGracefully();
		}
	}
	
	private class ChannelHandler extends ChannelInitializer<SocketChannel>{
		protected void initChannel(SocketChannel sc) throws Exception {
			sc.pipeline().addLast(new ClientHandler());
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
		new Client().connect("127.0.0.1", port);
	}

}
