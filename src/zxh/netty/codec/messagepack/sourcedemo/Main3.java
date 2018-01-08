package zxh.netty.codec.messagepack.sourcedemo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;

import org.msgpack.MessagePack;
import org.msgpack.packer.Packer;
import org.msgpack.unpacker.Unpacker;

/**
* 基本类型的编解码
 */
public class Main3 {
	public static void main(String[] args) throws IOException{
		MessagePack msgPack = new MessagePack();
		
		//序列化
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Packer packer = msgPack.createPacker(out);
		
		// Serialize values of primitive types
        packer.write(true); // boolean value
        packer.write(10); // int value
        packer.write(10.5); // double value
 
        // Serialize objects of primitive wrapper types
        packer.write(Boolean.TRUE);
        packer.write(new Integer(10));
        packer.write(new Double(10.5));
 
        // Serialize various types of arrays
        packer.write(new int[] { 1, 2, 3, 4 });
        packer.write(new Double[] { 10.5, 20.5 });
        packer.write(new String[] { "msg", "pack", "for", "java" });
        packer.write(new byte[] { 0x30, 0x31, 0x32 }); // byte array
 
        // Serialize various types of other reference values
        packer.write("MessagePack"); // String object
        packer.write(ByteBuffer.wrap(new byte[] { 0x30, 0x31, 0x32 })); // ByteBuffer object
        packer.write(BigInteger.ONE); // BigInteger object
        
        
        
        
        // 反序列化
        byte[] bytes = out.toByteArray();
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        Unpacker unpacker = msgPack.createUnpacker(in);
        
        // to primitive values
        boolean b = unpacker.readBoolean(); // boolean value
        int i = unpacker.readInt(); // int value
        double d = unpacker.readDouble(); // double value
 
        // to primitive wrapper value
        Boolean wb = unpacker.read(Boolean.class);
        Integer wi = unpacker.read(Integer.class);
        Double wd = unpacker.read(Double.class);
 
        // to arrays
        int[] ia = unpacker.read(int[].class);
        Double[] da = unpacker.read(Double[].class);
        String[] sa = unpacker.read(String[].class);
        byte[] ba = unpacker.read(byte[].class);
 
        // to String object, ByteBuffer object, BigInteger object, List object and Map object
        String ws = unpacker.read(String.class);
        ByteBuffer buf = unpacker.read(ByteBuffer.class);
        BigInteger bi = unpacker.read(BigInteger.class);
        
        //打印
        System.out.println("boolean="+b+" int="+i+" double="+d +" Boolean="+wb+" Integer="+wi+" Double="+wd+"String="+ws);
        System.out.println("int[]="+ia+" int="+i+" Double[]="+da +" String[]="+sa+" byte[]="+ba+"  ByteBuffer="+buf+" BigInteger="+bi);
	}
}
