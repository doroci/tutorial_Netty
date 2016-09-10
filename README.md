# Netty
 - 비동기 이벤트 기반 네트워크 어플리케이션 프레임워크

# 1. BootStrap
  - 전송계층(소켓 모드 및 I/O 종류)
  - 이벤트 루프(단일 스레드 다중스레드)
  - 채널 파이프라인 설정
  - 소켓 주소와 포트
  - 소켓 옵션

# 1.1. ServerBootStrap
  - NioEventLoopGroup 객체 (nonBlocking) 지원
  - OioEventLoopGroup 객체 (blocking) 지원
  - EpollEventLoopGroup 객체 (epoll I/O mode) 지원

    # 1.1.1 group
        이벤트 루프 설정
    # 1.1.2 channel
        소켓 입출력 모드 설정
    # 1.1.3 channelFactory
        소켓 입출력 모드 설정
    # 1.1.4 handler
        서버 소켓 채널의 이벤트 핸들러 설정
    # 1.1.5 option
        서버 소켓 채널의 소켓 옵션 설정
    # 1.1.6 childOption
        소켓 채널의 소켓 옵션 설정








  
