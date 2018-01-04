package zhx.netty.helloworld;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Client {
	
	public static void main(String[] args) throws InterruptedException{
		//1、创建线程组
		EventLoopGroup group = new NioEventLoopGroup();
		
		//2、创建工具辅助类，用于服务器管道一系列配置
		Bootstrap b = new Bootstrap();
		b.group(group)
		.channel(NioSocketChannel.class)//指定NIO模式
		.handler(new ChannelInitializer<SocketChannel>() {//事件处理类,ChannelInitializer帮助使用者配置一个新的Channel

			@Override
			protected void initChannel(SocketChannel sc) throws Exception {
				//3 在这里配置具体数据接收方法的处理
				sc.pipeline().addLast(new ClientHandler());
				
			}
		});
		
		ChannelFuture cf = b.connect("127.0.0.1", 8765).sync();//建立连接
		
		cf.channel().writeAndFlush(Unpooled.copiedBuffer("11111".getBytes()));//写入通讯信息到管道
		
		cf.channel().closeFuture().sync();// 等待关闭
		
		group.shutdownGracefully();
	}

}
