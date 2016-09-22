package echo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
/**
 * Created by lee on 2016. 7. 10..
 */
public class EchoClientHandler extends ChannelInboundHandlerAdapter{

    private final ByteBuf firstMessage;

    /**
     * Creates a client-side handler.
     */
    public EchoClientHandler() {
        //보낼 메시지
        firstMessage = Unpooled.buffer(EchoClient.SIZE);
        for (int i = 0; i < firstMessage.capacity(); i ++) {
            firstMessage.writeByte((byte) i);
        }
    }

    //소켓이 최초 활성화 되었을 때 호출
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        //System.out.println("전송한 메시지 :" +firstMessage);
        ctx.writeAndFlush(firstMessage);
    }

    //서버로부터 수신된 데이터가 있을때 호출
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
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
