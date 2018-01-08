package zxh.netty.codec.messagepack;

import java.util.List;

import org.msgpack.MessagePack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

/**
* @Description: 解码器
 */
public class MsgPackDecoder extends MessageToMessageDecoder<ByteBuf> {


	@Override
	protected void decode(ChannelHandlerContext chc, ByteBuf byteBuf,List<Object> list) throws Exception {
		int length = byteBuf.readableBytes();
		byte[] bytes = new byte[length];
		byteBuf.getBytes(byteBuf.readerIndex(), bytes, 0, length);
		MessagePack msgPack = new MessagePack();
		list.add(msgPack.read(bytes));
	}

}
