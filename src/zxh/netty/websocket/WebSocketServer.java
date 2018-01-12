package zxh.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;

public class WebSocketServer {
	
	public void run(int port){
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workGroup = new NioEventLoopGroup();
		try{
			ServerBootstrap  b = new ServerBootstrap();
			b.group(bossGroup, workGroup)
			.channel(NioServerSocketChannel.class)
			.childHandler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel sc) throws Exception {
					ChannelPipeline pipeline = sc.pipeline();
					// 服务端，对请求解码  
					pipeline.addLast("http-codec", new HttpServerCodec());
					// 聚合器，把多个消息转换为一个单一的FullHttpRequest或是FullHttpResponse  
					pipeline.addLast("aggregator", new HttpObjectAggregator(65536));
					 // 块写入处理器
					pipeline.addLast("http-chunked",new ChunkedWriteHandler());
					//自定义处理器
					pipeline.addLast("handler", new WebSocketServerHandler());
					
				}
			});
			
			ChannelFuture cf = b.bind(port).sync();
			
			System.out.println("Web socket server started at port " + port + '.');
			System.out.println("Open your browser and navigate to http://localhost:" + port + '/');
			
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
		if (args.length > 0) {
		    try {
			port = Integer.parseInt(args[0]);
		    } catch (NumberFormatException e) {
			e.printStackTrace();
		    }
		}
		
		new WebSocketServer().run(port);

	}

}
