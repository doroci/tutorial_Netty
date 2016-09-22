# Netty
 - 비동기 이벤트 기반 네트워크 어플리케이션 프레임워크

# BootStrap
  - 전송계층(소켓 모드 및 I/O 종류)
  - 이벤트 루프(단일 스레드 다중스레드)
  - 채널 파이프라인 설정
  - 소켓 주소와 포트
  - 소켓 옵션

# ServerBootStrap.class
  - NioEventLoopGroup 객체 (nonBlocking) 지원
  - OioEventLoopGroup 객체 (blocking) 지원
  - EpollEventLoopGroup 객체 (epoll I/O mode) 지원

    # 1. group
        이벤트 루프 설정
    # 2. channel
        소켓 입출력 모드 설정
    # 3. channelFactory
        소켓 입출력 모드 설정(channel과 동일한 역할을 함)
    # 4. handler
        서버 소켓 채널의 이벤트 핸들러 설정
    # 5. childHandler
        소켓 채널의 데이터 가공 핸들러 설정
    # 6. option
        서버 소켓 채널의 소켓 옵션 설정
    # 7. childOption
        소켓 채널의 소켓 옵션 설정
    # ...

# Bootstrap.class
  - ServerBootStrap과 유사
  - 단일 소켓 지원

    # 1. group
        단일 루프 설정만 가능
    # 2. chnnel
        소켓 입출력 모드 설정
    # 3. channelFactory
        소켓 입출력 모드 설정
    # 4. handler
        클라이언트 소켓의 이벤트 핸들러 설정
    # 5. option
        소켓 채널의 소켓 옵션 설정
    # ...

# ChannelInboundHandler
  - 인바운드 이벤트 발생 순서

        1. ChannelRegistered
        2. channelAcitve
        3. channelRead
        4. channelReadComplete
        5. channelInactive
        6. channelUnregistered

# ChannelOutboundHandler
  - 아웃바운드 이벤트 발생

        bind() : 소켓 채널이 사용하는 IP, PORT 정보 확인.
        connect() : 서버와 클라이언트의 연결.
        disconnect() : 소켓 채널의 연결이 끊김.
        close() : 소켓 채널의 연결이 닫힘.
        write() : 소켓 채널에 데이터를 기록
        flush() : 소켓 채널에 대기중인 모든 메시지를 보낸다.

# Codec
 - 인코더와 디코더를 기본으로 제공

        base64 : base64 인코딩 데이터에 대한 송수신을 지원
        bytes : 바이트 배열 데이터에 대한 송수신을 지원
        compression : 송수신 데이터의 압축을 지원
        haproxy :
        http : http 프로토콜을 지원
        marshalling : marshalling(객체를 네트워크를 통해 송신 가능한 형태로 변환 과정)을 지원
        protobuf : 구글 프로토콜 버퍼를 사용한 데이터 송수신을 지원
        rtsp : Real Time Streaming Protocal을 지원
        sctp : TCP가 아닌 SCTP 전송 계층을 사용을 지원
        serialization : 자바의 객체를 네트워크로 전송을 지원
        socks : 서버-클라이언트 간의 TCP나 UDP 통신을 프록시 서버를 거쳐 진행하도록 해 주는 프로토콜을 지원
        spdy : 구글의 SPDY 프로토콜을 지원
        string : 문자열의 송수신을 지원

# Event Loops
 - 네티는 싱글 쓰레드와 멀티 쓰레드 모두 지원
 - 멀티쓰레드는 CPU를 최대한 활용을 할 수 있지만 제약도 많다.(Context Switch, 쓰레드 경합 등으로 인한 오버해드 ). 그래서 비동기 네트워크 프레임워크인 Vert.x 나 Node.js 등은 싱글 쓰레드를 사용한다. 하지만 네티는 멀티 쓰레드의 단점을 극복 할 수 있는 모델링을(하지만 멀티 쓰레드의 optimize를 사용 하기 위해선 쓰레드 숫자를 적절하게 설정해 줘야한다) 구현함


    일반적인 이벤트 루프 : 객체(이벤트 발생) -> 이벤트 큐 -> 이벤트 루프(다수의 쓰레드가 이벤트 큐에서 꺼내서 일을 함.)

    네티의 멀티 쓰레드 이벤트 루프 : 채널(이벤트 발생) -> 이벤트 큐 -> 이벤트 루프(다수의 쓰레드가 이벤트 큐에서 꺼내서 일을 함.)
     ex) Channel1, Channel2, Channel 3 -> 이벤트 큐A -> 이벤트 루프
         Channel4 -> 이벤트 큐B -> 이벤트 루프
     즉, 채널을 이용하여 이벤트 큐와 이벤트 루프가 독립적으로 실행된다.