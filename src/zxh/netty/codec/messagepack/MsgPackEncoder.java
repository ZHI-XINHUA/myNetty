package zxh.netty.codec.messagepack;

import org.msgpack.MessagePack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
* @Description:编码器
 */
public class MsgPackEncoder extends MessageToByteEncoder<Object> {

	@Override
	protected void encode(ChannelHandlerContext chc, Object obj, ByteBuf byteBuf)
			throws Exception {
		MessagePack msgPack = new MessagePack();
		//序列化
		byte[] bytes = msgPack.write(obj);
		//序列化后的字节写入到ByteBuf
		byteBuf.readBytes(bytes);
		
	}

}
