package echo;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;

/**
 * Created by lee on 2016. 7. 9..
 */
public class EchoServerHandler extends ChannelInboundHandlerAdapter{

    //클라이언트로 부터 수신할 데이터가 있을 때 호출
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("전송받은 메시지 :" +msg);
        ctx.write(msg);
    }

    //수신된 데이터를 모두 읽었을 때 호출
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
