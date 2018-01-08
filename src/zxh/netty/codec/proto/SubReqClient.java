package zxh.netty.codec.proto;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;



/**
 * Created by zhixinhua on 18/1/8.
 */
public class SubReqClient {

    public void connect(String host,int port){
        EventLoopGroup group = new NioEventLoopGroup();
        try{
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel sc) throws Exception {

                            sc.pipeline().addLast(new ProtobufVarint32FrameDecoder());
                            sc.pipeline().addLast(new ProtobufDecoder(SubscribeRespProto.SubscribeReq.getDefaultInstance()));
                            sc.pipeline().addLast(new ProtobufVarint32LengthFieldPrepender());
                            sc.pipeline().addLast(new ProtobufEncoder());
                            sc.pipeline().addLast(new SubReqClientHandler());
                        }
                    });

            ChannelFuture cf = b.connect(host,port).sync();
            cf.channel().closeFuture();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
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

        new SubReqClient().connect("127.0.0.1",port);

    }
}
