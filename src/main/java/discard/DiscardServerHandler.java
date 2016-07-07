package discard;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by lee on 2016. 7. 8..
 */
public class DiscardServerHandler extends SimpleChannelInboundHandler<Object>{

    public void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
       //아무것도 하지 않음
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
