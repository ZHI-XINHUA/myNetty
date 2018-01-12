package zhx.netty.httpfileserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

public class HttpFileServer {

	private static final String DEFAULT_URL = "/sources/";
	
	public void run(int port,String url){
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workGroup = new NioEventLoopGroup();
		try{
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workGroup)
			.channel(NioServerSocketChannel.class)
			.childHandler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel sc) throws Exception {
					ChannelPipeline pipeline = sc.pipeline();
					// 加入http的解码器:负责把字节解码成Http请求
					pipeline.addLast("http-decoder", new HttpRequestDecoder());
					// 加入ObjectAggregator解码器，作用是他会把多个消息转换为单一的FullHttpRequest或者FullHttpResponse
					pipeline.addLast("http-aggregator", new HttpObjectAggregator(65536));
					// 加入http的编码器:当Server处理完消息后，需要向Client发送响应。那么需要把响应编码成字节，再发送出去
					pipeline.addLast("http-encoder",new HttpResponseEncoder());
					// 加入chunked 主要作用是支持异步发送的码流（大文件传输），但不专用过多的内存，防止java内存溢出
					pipeline.addLast("http-chunked", new ChunkedWriteHandler());
					// 加入自定义处理文件服务器的业务逻辑handler
					pipeline.addLast("fileServerHandler",new HttpFileServerHandler(url));
					
					
				}
			});
			
			ChannelFuture cf = b.bind("192.168.100.108", port).sync();
			
			System.out.println("HTTP文件目录服务器启动，网址是 : " + "http://192.168.100.108:"  + port + url);
			
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
		String url = DEFAULT_URL;
		new HttpFileServer().run(port, url);

	}

}
