package zxh.netty.codec.messagepack;

import zxh.netty.codec.marshalling.MarshallingCodeCFactory;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

public class Client {
	
	public void connect(String host,int port){
		EventLoopGroup group = new NioEventLoopGroup();
		try{
			Bootstrap b = new Bootstrap();
			b.group(group)
			.channel(NioSocketChannel.class)
			.option(ChannelOption.TCP_NODELAY, true)
			.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
			.handler(new ChannelInitializer<SocketChannel>() {

				@Override
				protected void initChannel(SocketChannel sc) throws Exception {
					sc.pipeline().addLast("frameDecoder",new LengthFieldBasedFrameDecoder(65535, 0, 8,0,8));
					sc.pipeline().addLast("msgPack Decoder",new MsgPackDecoder());
					sc.pipeline().addLast("frameEncoder",new LengthFieldPrepender(8));
					sc.pipeline().addLast("msgPack Encoder",new MsgPackEncoder());
					sc.pipeline().addLast(new ClientHandler());
					
				}
			});
			
			ChannelFuture cf = b.connect(host, port).sync();
			cf.channel().closeFuture().sync();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			
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
