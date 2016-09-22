package echo;

import echoTest.EchoServerV3SecondHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;

/**
 * Created by lee on 2016. 7. 10..
 */
public class EchoClient {


    static final boolean SSL = System.getProperty("ssl") != null;
    static final String HOST = System.getProperty("host", "127.0.0.1");
    static final int PORT = Integer.parseInt(System.getProperty("port", "8007"));
    static final int SIZE = Integer.parseInt(System.getProperty("size", "256"));

    public static void main(String[] args) throws Exception {
        // SSL 설정
        final SslContext sslCtx;
        if (SSL) {
            sslCtx = SslContextBuilder.forClient()
                    .trustManager(InsecureTrustManagerFactory.INSTANCE).build();
        } else {
            sslCtx = null;
        }

        //클라이언트 설정
        EventLoopGroup group = new NioEventLoopGroup(); //스레드 그룹 (데이터 I/O 및 이벤트 처리, CPU코어수에 따른 수레드 수가 설정)
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)  //이벤트 루프 그룹 설정 (서버와 달리 클라이언트는 채널이 1개)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            if (sslCtx != null) {
                                p.addLast(sslCtx.newHandler(ch.alloc(), HOST, PORT));
                            }
                            //채널 파이프라인에 EchoClientHandler 등록
                            p.addLast(new EchoClientHandler());
                        }
                    });

            // 클라이언트 접속 시도(비동기 처리)
            ChannelFuture f = b.connect(HOST, PORT).sync();

            // 소켓이 닫힐 때 까지 대기
            f.channel().closeFuture().sync();
        } finally {
            // 모든 쓰레드에서 이벤트 루프를 제거한다.
            group.shutdownGracefully();
        }
    }
}
