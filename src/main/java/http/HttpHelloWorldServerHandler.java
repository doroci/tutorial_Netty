package http;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;

import static io.netty.handler.codec.http.HttpHeaders.Names.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;

/**
 * Created by lee on 2016. 9. 17..
 */
public class HttpHelloWorldServerHandler extends ChannelInboundHandlerAdapter {

    private final static byte[] contents  = {'H','e','l','l','o',' ','W','o','r','l','d'};

    //수신한 데이터를 모두 읽었을때 호출
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();    //채널의 버퍼를 비운다.
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    //수신한 데이터가 있을때 호출
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        // HttpReq, HttpRes 처리
        if (msg instanceof HttpRequest) {
            HttpRequest req = (HttpRequest) msg;

            if (HttpHeaders.is100ContinueExpected(req))
                ctx.write(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,HttpResponseStatus.CONTINUE));

            boolean keepAlive = HttpHeaders.isKeepAlive(req);
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                    HttpResponseStatus.OK, Unpooled.wrappedBuffer(contents));

            response.headers().set(CONTENT_TYPE ,"text/plain");
            response.headers().set(CONTENT_LENGTH , response.content().readableBytes());

            if (keepAlive)
                ctx.write(response).addListener(ChannelFutureListener.CLOSE);
            else {
                response.headers().set(CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
                ctx.write(response);
            }

        }


    }
}
