package zxh.netty.ssl.twoway;

import javax.net.ssl.SSLEngine;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.ssl.SslHandler;

public class SecureChatClientInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		String cChatPath =  System.getProperty("user.dir")+"/src/zxh/netty/ssl/conf/twoway/cChat.jks";
		
		SSLEngine engine = SecureChatSslContextFactory.getClientContext(cChatPath,cChatPath).createSSLEngine();
		engine.setUseClientMode(true);
		pipeline.addLast("ssl", new SslHandler(engine));

		// On top of the SSL handler, add the text line codec.
		pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192,Delimiters.lineDelimiter()));
		pipeline.addLast("decoder", new StringDecoder());
		pipeline.addLast("encoder", new StringEncoder());

		// and then business logic.
		pipeline.addLast("handler", new SecureChatClientHandler());
	}

}
