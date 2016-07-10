package echo;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.sctp.nio.NioSctpServerChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

/**
 * Created by lee on 2016. 7. 9..
 */
public class EchoServer {


    static final boolean SSL = System.getProperty("ssl") != null;
    static final int PORT = Integer.parseInt(System.getProperty("port", "8007"));

    public static void main(String[] args) throws Exception {
        //SSL 설정
        final SslContext sslCtx;
        if (SSL) {
            SelfSignedCertificate ssc = new SelfSignedCertificate();
            sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
        } else {
            sslCtx = null;
        }

        // 서버 설정
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);    //부모 스레드 그룹 (클라이언트 연결 요청 처리, 1일경우 단일 쓰레드)
        EventLoopGroup workerGroup = new NioEventLoopGroup();   //자식 스레드 그룹 (데이터 I/O 및 이벤트 처리, CPU코어수에 따른 수레드 수가 설정)
        try {
            ServerBootstrap b = new ServerBootstrap(); //클라이언트가 사용하기 위한 채널을 생성해준다.
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)  //bossGroup이 사용할 네트워크 입출력 모드를 설정
                    .option(ChannelOption.SO_BACKLOG, 100)  //채널 옵션 설정
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() { //workerGroup 채널의 초기화 방법을 설정
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {    // 채널 초기화
                            ChannelPipeline p = ch.pipeline();  //채널 파이프라인 인스턴스 생성
                            if (sslCtx != null) {
                                p.addLast(sslCtx.newHandler(ch.alloc()));
                            }
                            //채널 파이프라인에 EchoServerHandler 등록
                            p.addLast(new EchoServerHandler());
                        }
                    });

            // 서버 접속 시도 (비동기 처리)
            ChannelFuture f = b.bind(PORT).sync();

            // 서버 소켓이 닫힐 때까지 대기
            f.channel().closeFuture().sync();
        } finally {
            //모든 쓰레드에서 이벤트 루프 제거
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
