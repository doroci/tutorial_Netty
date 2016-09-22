package http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslContext;


/**
 * Created by lee on 2016. 9. 17..
 */
public class HttpHelloWorldServerInitializer extends ChannelInitializer<SocketChannel> {

    public HttpHelloWorldServerInitializer(SslContext sslCtx) {
        this.sslCtx = sslCtx;
    }

    private final SslContext sslCtx;
    //채널 초기화
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();  //채널 파이프라인 생성
        if (sslCtx != null) {
            p.addLast(sslCtx.newHandler(ch.alloc()));   // 새로운 핸들러에 소켓채널을 할당을 등록
        }
            p.addLast(new HttpServerCodec());   // 채널 파이프 라인에 HttpServerCodec을 등록
            p.addLast(new HttpHelloWorldServerHandler());   // 채널 파이프라인에 HttpHelloWorldServerHandler등록
    }
}
