package zxh.netty.frame.delimiter;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class Client {
	
	public void connect(String host,int port){
		EventLoopGroup group = new NioEventLoopGroup();
		try{
			Bootstrap b = new Bootstrap();
			b.group(group)
			.channel(NioSocketChannel.class)
			.option(ChannelOption.TCP_NODELAY, true)
			.handler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel sc) throws Exception {
					ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
					sc.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
					sc.pipeline().addLast(new StringDecoder());
					sc.pipeline().addLast(new ClientHandler());
				}
			});
			
			// 发起异步连接操作
		    ChannelFuture cf = b.connect(host, port).sync();
		    
		    // 当代客户端链路关闭
		    cf.channel().closeFuture().sync();
		    
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			 // 优雅退出，释放NIO线程组
			group.shutdownGracefully();
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
			
		new Client().connect("127.0.0.1",port);

	}

}
