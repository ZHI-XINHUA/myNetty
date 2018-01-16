package zxh.netty.protocol.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public final class Header implements Serializable {
	private static final long serialVersionUID = 1L;

    //消息验证码 3部分组成
    //0xABEF：固定值，表示是Netty协议消息，2个字节
    //主版本号：1~255 1个字节
    //次版本号：1~255 1个字节
    private int crcCode = 0xabef0101;
    //消息长度
    private int length;
    private long sessionID;
    //类型
    private byte type;
    //消息优先级 0-255
    private byte priority;
    //可选字段，用户扩展消息头
    private Map<String,Object> attachment = new HashMap<String, Object>();

    public final int getCrcCode() {
        return crcCode;
    }

    public final void setCrcCode(int crcCode) {
        this.crcCode = crcCode;
    }

    public final int getLength() {
        return length;
    }

    public final void setLength(int length) {
        this.length = length;
    }

    public final long getSessionID() {
        return sessionID;
    }

    public final void setSessionID(long sessionID) {
        this.sessionID = sessionID;
    }

    public final byte getType() {
        return type;
    }

    public final void setType(byte type) {
        this.type = type;
    }

    public final byte getPriority() {
        return priority;
    }

    public final void setPriority(byte priority) {
        this.priority = priority;
    }

    public final Map<String, Object> getAttachment() {
        return attachment;
    }

    public final void setAttachment(Map<String, Object> attachment) {
        this.attachment = attachment;
    }

    @Override
    public String toString() {
        return "Header{" +
                "crcCode=" + crcCode +
                ", length=" + length +
                ", sessionID=" + sessionID +
                ", type=" + type +
                ", priority=" + priority +
                ", attachment=" + attachment +
                '}';
    }
}
