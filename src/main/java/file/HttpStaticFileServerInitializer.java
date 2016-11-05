package file;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * Created by lee on 2016. 9. 11..
 */
public class HttpStaticFileServerInitializer extends ChannelInitializer<SocketChannel> {

    private final SslContext sslCtx;

    public HttpStaticFileServerInitializer(SslContext sslCtx) {
        this.sslCtx = sslCtx;
    }

    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeLine = ch.pipeline();
        if (sslCtx != null){
            pipeLine.addLast(sslCtx.newHandler(ch.alloc()));
        }
        pipeLine.addLast(new HttpServerCodec());
        pipeLine.addLast(new HttpObjectAggregator(65536));
        pipeLine.addLast(new ChunkedWriteHandler());
        pipeLine.addLast(new HttpStaticFileServerHandler());
    }
}
