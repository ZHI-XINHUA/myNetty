package zxh.netty.protocol.model;

import java.io.Serializable;

public final class NettyMessage implements Serializable {
	 private static final long serialVersionUID = 1L;
	    private Header header;
	    private Object body;

	    public final Header getHeader() {
	        return header;
	    }

	    public final void setHeader(Header header) {
	        this.header = header;
	    }

	    public final Object getBody() {
	        return body;
	    }

	    public final void setBody(Object body) {
	        this.body = body;
	    }

	    @Override
	    public String toString() {
	        return "NettyMessage{" +
	                "header=" + header +
	                ", body=" + body +
	                '}';
	    }
}
