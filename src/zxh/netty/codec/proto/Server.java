package zxh.netty.codec.proto;

import java.io.IOException;

public class Server {
	
	public static void main(String[] args) {
        String protoFile = "person-entity.proto";//  
        String strCmd = "F:/tool/protobuf-master/src/protoc.exe -I=D:/t2 --java_out=D:/t1 D:/t1/"+ protoFile;  
        try {
            Runtime.getRuntime().exec(strCmd);
        } catch (IOException e) {
            e.printStackTrace();
        }//通过执行cmd命令调用protoc.exe程序  
    }

}
