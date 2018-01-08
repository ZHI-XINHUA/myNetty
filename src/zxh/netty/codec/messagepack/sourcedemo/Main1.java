package zxh.netty.codec.messagepack.sourcedemo;

import org.msgpack.MessagePack;
import org.msgpack.annotation.Message;
import org.msgpack.annotation.Optional;

//官方demo：https://github.com/msgpack/msgpack-java/wiki/QuickStart-for-msgpack-java-0.6.x-(obsolete)

/**
* 对一个类的编解码
*  使用@Message注解可以序列化public修饰的变量
 */
public class Main1 {
	@Message
	public static class MyMessage{
		// public fields are serialized.
        public String name;
        public double version;
        
        // new field
        @Optional
        public int flag = 1;
	}
	
	public static void main(String[] args) throws Exception {
        MyMessage src = new MyMessage();
        src.name = "msgpack";
        src.version = 0.6;
        src.flag=100;
 
        MessagePack msgpack = new MessagePack();
        
        //如果不使用@Message注解表示要序列化的对象类，也可以通过注册方法可序列化的类的对象
        //msgpack.register(MyMessage.class);
        
        
        // 序列化
        byte[] bytes = msgpack.write(src);//对象序列化化后的字节数组
        
        // 反序列化 
        MyMessage dst = msgpack.read(bytes, MyMessage.class);//字节转换为对象
        
        
        System.out.println("name="+dst.name+" version="+dst.version+" flag="+dst.flag);
    }
}
