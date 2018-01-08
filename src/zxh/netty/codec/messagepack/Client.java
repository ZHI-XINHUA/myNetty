package zxh.netty.codec.messagepack;

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
					 //LengthFieldBasedFrameDecoder用于处理半包消息
                    //这样后面的MsgpackDecoder接收的永远是整包消息
					sc.pipeline().addLast("frameDecoder",new LengthFieldBasedFrameDecoder(65535,0,2,0,2));
					sc.pipeline().addLast("msgpack decoder",new MsgPackDecoder());
                    //在ByteBuf之前增加2个字节的消息长度字段
					sc.pipeline().addLast("frameEncoder",new LengthFieldPrepender(2)); 
					sc.pipeline().addLast("msgpack encoder",new MsgPackEncoder());
					sc.pipeline().addLast(new ClientHandler());
					
				}
			});
			
			ChannelFuture cf = b.connect(host, port).sync();
			cf.channel().closeFuture().sync();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
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
