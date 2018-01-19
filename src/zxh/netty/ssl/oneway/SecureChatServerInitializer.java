package zxh.netty.ssl.oneway;

import javax.net.ssl.SSLEngine;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.ssl.SslHandler;

public class SecureChatServerInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel sc) throws Exception {
		ChannelPipeline pipeline = sc.pipeline();
		String sChatPath = (System.getProperty("user.dir")+ "/src/zxh/netty/ssl/conf/oneway/sChat.jks");
		
		SSLEngine engine = SecureChatSslContextFactory.getServerContext(sChatPath).createSSLEngine();
		engine.setUseClientMode(false);
		
		pipeline.addLast("ssl", new SslHandler(engine));

		// On top of the SSL handler, add the text line codec.
		pipeline.addLast("framer", new DelimiterBasedFrameDecoder(8192,Delimiters.lineDelimiter()));
		pipeline.addLast("decoder", new StringDecoder());
		pipeline.addLast("encoder", new StringEncoder());

		// and then business logic.
		pipeline.addLast("handler", new SecureChatServerHandler());
	}

}
