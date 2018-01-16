package zxh.netty.protocol.coder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.marshalling.MarshallerProvider;


public class MarshallingEncoder extends io.netty.handler.codec.marshalling.MarshallingEncoder {

    public MarshallingEncoder(MarshallerProvider provider) {
        super(provider);
    }
    @Override
    public void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        super.encode(ctx, msg, out);
    }

}

