package zxh.netty.protocol.model;

public enum MessageType {
	
	/**
     * 握手请求消息
     */
    LOGIN_REQ((byte)3),
    /**
     * 握手应答消息
     */
    LOGIN_RESP((byte)4),
    /**
     * 心跳请求消息
     */
    HEARTBEAT_REQ((byte)5),
    /**
     * 心跳应答消息
     */
    HEARTBEAT_RESP((byte)6),
    ;
    
    byte value;

    MessageType(byte i) {
        this.value = i;
    }

    public byte value() {
        return this.value;
    }

    
   
}
