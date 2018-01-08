package zxh.netty.codec.messagepack.sourcedemo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.msgpack.MessagePack;
import org.msgpack.packer.Packer;
import org.msgpack.template.Template;
import org.msgpack.template.Templates;
import org.msgpack.unpacker.Unpacker;

import static org.msgpack.template.Templates.tList;
import static org.msgpack.template.Templates.tMap;


/**
* List, Map编解码 
 */
public class Main4 {

	public static void main(String[] args) throws IOException {
		MessagePack msgPack = new MessagePack();
		
		 // Create templates for serializing/deserializing List and Map objects
		Template<List<String>> listTmpl = tList(Templates.TString);
		Template<Map<String,String>> mapTmpl = tMap(Templates.TString,Templates.TString);
		
		//
        // Serialization
        //
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		Packer packer = msgPack.createPacker(out);
		
		 // Serialize List object
        List<String> list = new ArrayList<String>();
        list.add("msgpack");
        list.add("for");
        list.add("java");
        packer.write(list); // List object
        
        // Serialize Map object
        Map<String, String> map = new HashMap<String, String>();
        map.put("sadayuki", "furuhashi");
        map.put("muga", "nishizawa");
        packer.write(map); // Map object
 
        
        
        
        //
        // Deserialization
        //
        byte[] bytes = out.toByteArray();
        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        Unpacker unpacker = msgPack.createUnpacker(in);
       
        // to List object
        List<String> dstList = unpacker.read(listTmpl);
 
        // to Map object
        Map<String, String> dstMap = unpacker.read(mapTmpl);
        
        
        //打印结果
        System.out.println(dstList);
        System.out.println(dstMap);
	}
        
		

}
