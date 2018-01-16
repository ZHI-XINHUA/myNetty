package zxh.netty.heartbeat_1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

public class Server {
	private static final int READ_IDEL_TIME_OUT = 5; // 读超时
    private static final int WRITE_IDEL_TIME_OUT = 0;// 写超时
    private static final int ALL_IDEL_TIME_OUT = 0; // 所有超时
	
	public void run(int port) throws InterruptedException{
		EventLoopGroup bossGroup = new NioEventLoopGroup();
	    EventLoopGroup workerGroup = new NioEventLoopGroup();
		try{
			 ServerBootstrap b = new ServerBootstrap();
			 b.group(bossGroup,workerGroup)
			 .channel(NioServerSocketChannel.class)
			 .option(ChannelOption.SO_BACKLOG, 128)   
			 .childOption(ChannelOption.SO_KEEPALIVE, true)
			 .localAddress(new InetSocketAddress(port))
			 .childHandler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel sc) throws Exception {
					ChannelPipeline pipeline = sc.pipeline();
					pipeline.addLast(new IdleStateHandler(READ_IDEL_TIME_OUT, WRITE_IDEL_TIME_OUT, ALL_IDEL_TIME_OUT, TimeUnit.SECONDS));
					pipeline.addLast(new StringDecoder());
					pipeline.addLast(new StringEncoder());
					pipeline.addLast(new ServerHandler());
				}
			});
			 
			// 绑定端口，开始接收进来的连接
             ChannelFuture future = b.bind(port).sync();  
             
             future.channel().closeFuture().sync();
		}catch(Exception e){
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8080;
        }
        
        new Server().run(port);

	}

}
