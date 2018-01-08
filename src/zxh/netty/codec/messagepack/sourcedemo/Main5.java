package zxh.netty.codec.messagepack.sourcedemo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.msgpack.MessagePack;
import org.msgpack.template.Templates;
import org.msgpack.type.Value;
import org.msgpack.unpacker.Converter;

import static org.msgpack.template.Templates.tList;

public class Main5 {

	public static void main(String[] args) throws IOException {
		 // Create serialize objects.
        List<String> src = new ArrayList<String>();
        src.add("msgpack");
        src.add("kumofs");
        src.add("viver");
        
        MessagePack msgPack = new MessagePack();
        // Serialize
        byte[] raw = msgPack.write(src);
        
        // Deserialize directly using a template
        List<String> dst1 = msgPack.read(raw,tList(Templates.TString));
        
        // Or, Deserialze to Value then convert type.
        Value dynamic= msgPack.read(raw);
        List<String> dst2 =new Converter(dynamic).read(tList(Templates.TString));
        
        System.out.println(dst1);
        System.out.println(dst2);
        
        

	}

}
