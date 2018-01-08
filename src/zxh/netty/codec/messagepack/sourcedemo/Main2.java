package zxh.netty.codec.messagepack.sourcedemo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.msgpack.MessagePack;
import org.msgpack.annotation.Message;
import org.msgpack.packer.Packer;
import org.msgpack.unpacker.Unpacker;

////官方demo：https://github.com/msgpack/msgpack-java/wiki/QuickStart-for-msgpack-java-0.6.x-(obsolete)

/**
* 多个对象编解码
* 使用Packer和Unpacker 序列化和反序列化多个对象
*/
public class Main2 {
	@Message
	public static class MyMessage{
		// public fields are serialized.
        public String name;
        public double version;
        protected String isSerializeFlag;//protected private 都不会被序列化
        
	}

	public static void main(String[] args) throws IOException {
		 MyMessage src1 = new MyMessage();
        src1.name = "msgpack";
        src1.version = 0.6;
        MyMessage src2 = new MyMessage();
        src2.name = "muga";
        src2.version = 10.0;
        MyMessage src3 = new MyMessage();
        src3.name = "frsyukik";
        src3.version = 1.0;
        src3.isSerializeFlag = "true";
        
        MessagePack msgPack = new MessagePack();
        
        //序列化
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Packer packer = msgPack.createPacker(out);
        packer.write(src1);
        packer.write(src1);
        packer.write(src1);
        byte[] bytes = out.toByteArray();
        
        //反序列化
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        Unpacker unpacker = msgPack.createUnpacker(in);
        MyMessage mm1 = unpacker.read(MyMessage.class);
        MyMessage mm2 = unpacker.read(MyMessage.class);
        MyMessage mm3 = unpacker.read(MyMessage.class);
        System.out.println("mm1[name="+mm1.name+",version="+mm1.version+",isSerializeFlag="+mm1.isSerializeFlag+"]");
        System.out.println("mm2[name="+mm2.name+",version="+mm2.version+",isSerializeFlag="+mm2.isSerializeFlag+"]");
        System.out.println("mm3[name="+mm3.name+",version="+mm3.version+",isSerializeFlag="+mm3.isSerializeFlag+"]");
        
        

	}

}
