package zxh.netty.protocol.coder;

import java.util.HashMap;
import java.util.Map;

import zxh.netty.protocol.model.Header;
import zxh.netty.protocol.model.NettyMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class NettyMessageDecoder extends LengthFieldBasedFrameDecoder {
	MarshallingDecoder marshallingDecoder;

	public NettyMessageDecoder(int maxFrameLength, int lengthFieldOffset,int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
		super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment,initialBytesToStrip);
		marshallingDecoder =  MarshallingFactory.buildMarshallingDecoder();
	}
	
	@Override
	protected Object decode(ChannelHandlerContext ctx, ByteBuf buf) throws Exception {
		ByteBuf frame = (ByteBuf) super.decode(ctx, buf);
		if(frame==null){
			return null;
		}
		/*
		 * 不知道为什么这样设置就不行？？？？？？
		 * 
		NettyMessage message = new NettyMessage();
		Header header = new Header();
		header.setCrcCode(frame.readInt());
		header.setLength(frame.readInt());
		header.setPriority(frame.readByte());
		header.setSessionID(frame.readLong());
		header.setType(frame.readByte());*/
		
       
		
		NettyMessage message = new NettyMessage();
        Header header = new Header();
        header.setCrcCode(frame.readInt());
        header.setLength(frame.readInt());
        header.setSessionID(frame.readLong());
        header.setType(frame.readByte());
        header.setPriority(frame.readByte());
		
		int size = frame.readInt();
		if(size>0){
			Map<String, Object> attach = new HashMap<String, Object>(size);
			int keySize = 0;
            byte[] keyArray = null;
            String key = null;
            for (int i = 0; i < size; i++) {
                keySize = frame.readInt();
                keyArray = new byte[keySize];
                frame.readBytes(keyArray);
                key = new String(keyArray, "UTF-8");
                attach.put(key, marshallingDecoder.decode(ctx, frame));
            }
            keyArray = null;
            key = null;
            header.setAttachment(attach);
		}
		
		if (frame.readableBytes() > 4) {
            message.setBody(marshallingDecoder.decode(ctx, frame));
        }
        message.setHeader(header);
        return message;
	}

	
}
