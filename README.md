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


        * 일반적인 이벤트 루프 : 객체(이벤트 발생) -> 이벤트 큐 -> 이벤트 루프(다수의 쓰레드가 이벤트 큐에서 꺼내서 일을 함.)
        * 네티의 멀티 쓰레드 이벤트 루프 : 채널(이벤트 발생) -> 이벤트 큐 -> 이벤트 루프(다수의 쓰레드가 이벤트 큐에서 꺼내서 일을 함.)
         ex) Channel1, Channel2, Channel 3 -> 이벤트 큐A -> 이벤트 루프
         Channel4 -> 이벤트 큐B -> 이벤트 루프
        즉, 채널을 이용하여 이벤트 큐와 이벤트 루프가 독립적으로 실행된다.


 # Java Byte Buffer
 - 바이트 데이터를 저장하고 읽는 저장소이다.
 - 배열을 맴버 변수로 가지고 있으며 배열에 대한 읽고 쓰기를 추상화한 메서드를 제공한다.
 - 개발자가 배열의 인덱스에 대한 계산 없이 데이터의 변경 처리를 수행 할 수 있다.
 - ByteBuffer, CharBuffer, IntBuffer, ShortBuffer, LongBuffer, FloatBuffer, DobuleBuffer를 제공

        * 내부의 배열 상태를 관리하는 메서드
         capacity : 버퍼에 저장 할 수 있는 데이터의 최대 크기로 한번 저장하면 변경이 불가
         position : 읽기 또는 쓰기가 작업 중인 위치를 나타낸다. get, put 메서드가 호출되면 자동증가한다.
         limit : 읽고 쓸 수 있는 버퍼 공간의 최대치를 나타낸다.

        * 버퍼 객체 생성 메서드
         allocate : 힙 영역에 바이트 버퍼를 생성
         allocateDirect : OS 커널에 바이트 버퍼를 생성한다. ByteBuffer로만 생성가능
         wrap : 입력된 바이트 배열을 사용하여 바이트 버퍼를 생성

 # Netty Byte Buffer
 - Java Byte Buffer보다 성능적으로 우수하다
 - 네티 바이트 버퍼 풀은 빈번한 바이트 버퍼 할당과 해제에 대한 부담을 줄여줄어 GC에 부담을 덜어준다.
 - 특징

        * 별도의 읽기 인덱스와 쓰기 인덱스
        * flip 메서드 없이 읽기 쓰기 기능 ( Java Byte Buffer는 write를 전/후 에 항상 flip을 호출해야한다. 바이트 버퍼에서 사용하는 인덱스가 하나이기 때문, 하지만 네티는 read와 write 인덱스가 분리 되어있다)
        * 가변 바이트 버퍼 (capacity의 값을 변경하여도 기존의 값은 유지가 된다.)
        * 바이트 버퍼 풀
        * 복합 버퍼
            order() => 버퍼 생성이 아닌 바이트 버퍼의 내용을 공유하는 파생 바이트 버퍼 객체를 생성한다.
            getUnsignedByte : 기본형 byte, 반환형 short
            getUnsignedShort : 기본형 short, 반환형 int
            getUnsignedMedium : 기본형 medium, 반환형 int
            getUnsignedInt : 기본형 Int, 반환형 long
        * 자바의 바이트 버퍼와 네티의 바이트 버퍼 상호 호환



 - 종류

        * Heap Buffer
            - PooledHeapByteBuf, UnpooledHeapBuf
            - 버퍼 생성시 : ByteBufAllocator.DEFAULT.heapBuffer() , Unpooled.buffer()
        * direct Bufer
            - PooledDirectByteBuf, UnpooledDirectByteBuf
            - 버퍼 생성시 : ByteBufAllocator.DEFAULT.directBuffer(), Unpooled.directBuffer()







