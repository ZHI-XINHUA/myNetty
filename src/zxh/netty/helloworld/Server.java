package zxh.netty.helloworld;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class Server {
	public static void main(String[] args) throws InterruptedException{
		
	    //1、创建线两个程组
		EventLoopGroup pGroup = new NioEventLoopGroup();//用于服务端接受客户端连接
		EventLoopGroup cGroup = new NioEventLoopGroup();//用于网络通信（网络读写）
		
		//2、创建工具辅助类，用于服务器管道一系列配置
		ServerBootstrap sb = new ServerBootstrap();
		sb.group(pGroup, cGroup)
		.channel(NioServerSocketChannel.class) //指定NIO模式
		.option(ChannelOption.SO_BACKLOG, 1024) //设置tcp缓冲区大小
		.option(ChannelOption.SO_SNDBUF, 32*1024) //设置发送缓冲区大小
		.option(ChannelOption.SO_RCVBUF, 32*1024) //设置接受缓冲区大小
		.option(ChannelOption.SO_KEEPALIVE, true) //设置保持连接
		.childHandler(new ChannelInitializer<SocketChannel>() {//事件处理类,ChannelInitializer帮助使用者配置一个新的Channel

			@Override
			protected void initChannel(SocketChannel sc) throws Exception {
				//3 在这里配置具体数据接收方法的处理
				sc.pipeline().addLast(new ServerHandler());
				
			}
		});
		
		//4 进行绑定 
		ChannelFuture cf = sb.bind(8765).sync();
		
		//5 等待关闭
		cf.channel().closeFuture().sync();
		
		//释放资源
		pGroup.shutdownGracefully();
		cGroup.shutdownGracefully();
		
		
	}

}
