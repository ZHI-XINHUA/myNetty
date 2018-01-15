package zhx.netty.heartbeat_1;

import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;

public class Client {
    ChannelFuture future = null;

	public void connect(int port, String host) throws InterruptedException {
		EventLoopGroup group = new NioEventLoopGroup();
		try{
			 Bootstrap b = new Bootstrap();
			 b.group(group)
			 .channel(NioSocketChannel.class)
		     .option(ChannelOption.TCP_NODELAY, true)
		     .handler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel sc) throws Exception {
					 ChannelPipeline p = sc.pipeline();
                     p.addLast("decoder", new StringDecoder());
                     p.addLast("encoder", new StringEncoder());
                     p.addLast("ping", new IdleStateHandler(0, 4, 0, TimeUnit.SECONDS));
					
				}
			});
			 
			 future = b.connect(host, port).sync();
			 future.channel().writeAndFlush("Hello Netty Server ,I am a common client");
			 future.channel().closeFuture().sync();
		}finally{
			if(future!=null){
				if(future.channel() != null && future.channel().isOpen()){
					future.channel().close();
				}
			}
			
			 System.out.println("准备重连");
	         connect(port, host);
	         System.out.println("重连成功");
		}
	}
	
	
	public static void main(String[] args) throws InterruptedException {
		int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8080;
        }

        new Client().connect(port, "127.0.0.1");
	}

}
