package zhx.nio;

import java.nio.ByteBuffer;
import java.nio.channels.Selector;

/**
 * Created by zhixinhua on 17/12/25.
 */
public class Server implements  Runnable {
    //多路复用器（管理所有的通道）
    private Selector selector;
    //建立缓冲区
    private ByteBuffer buffer = ByteBuffer.allocate(1024);

    @Override
    public void run() {

    }
}
