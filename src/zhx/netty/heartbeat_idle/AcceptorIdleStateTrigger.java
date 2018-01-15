package zhx.netty.heartbeat_idle;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.channel.ChannelHandler.Sharable;

@Sharable
public class AcceptorIdleStateTrigger extends ChannelHandlerAdapter {
	 @Override
	 public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if(evt instanceof IdleStateEvent){
			IdleStateEvent event = (IdleStateEvent) evt;
			if(event.state() == IdleState.READER_IDLE){
				 throw new Exception("idle exception");
			}
		}else{
			super.userEventTriggered(ctx, evt);
		}
	 }

}
