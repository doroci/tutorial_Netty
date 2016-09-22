package http;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.local.LocalEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

import javax.net.ssl.SSLContext;
import java.security.cert.CertificateException;
/**
 * Created by lee on 2016. 9. 17..
 */
public class HttpHelloWorldServer {

    static final boolean SSL =  System.getProperty("ssl") != null;
    static final int PORT = Integer.parseInt(System.getProperty("port", SSL ? "8443" : "8080" ));

    public static void main(String[] args) throws Exception {

        //SSL 설정
        final SslContext sslCtx;
        if (SSL) {
            SelfSignedCertificate ssc = new SelfSignedCertificate();
            sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
        } else {
            sslCtx = null;
        }

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);    //부모 쓰레드 그룹 설정
        EventLoopGroup workerGroup = new NioEventLoopGroup();   //자식 쓰레드 그룹 설정

        try{
            ServerBootstrap b = new ServerBootstrap();  //채널 생성
            b.option(ChannelOption.SO_BACKLOG, 1024)    // 동시접속자 수를 1024까지 설정
             .group(bossGroup,workerGroup)  //이벤트 루프 설정
             .channel(NioServerSocketChannel.class) // NioServerSocketChannel로 소켓 입출력 설정
             .handler(new LoggingHandler(LogLevel.INFO))    // 로그 레벨을 INFO로 설정
             .childHandler(new HttpHelloWorldServerInitializer(sslCtx));    // HttpHelloWorldServerInitializer클래스를 이벤트 핸들러로 설정

            Channel ch = b.bind(PORT).sync().channel(); // 서버 접속 시도 (비동기)

            System.err.println("Open Your web browser and navigate to " +
                    (SSL ? "https" : "http") +":localhost :" +PORT +'/');
            ch.closeFuture().sync();    //소켓이 닫힐때까지 대기
        } finally {
            bossGroup.shutdownGracefully(); // 부모 쓰레드에서 이벤트 루프 제거
            workerGroup.shutdownGracefully();   //자식 쓰레드에서 이벤트 루프 제거
        }
    }
}
