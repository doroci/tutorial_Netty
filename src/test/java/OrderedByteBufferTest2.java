import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by lee on 2016. 9. 25..
 */
public class OrderedByteBufferTest2 {

    final String source = "Hello world";

    @Test
    public void convertNettyBufferToJavaBuffer(){

        ByteBuf buf = Unpooled.buffer(1);

        buf.writeBytes(source.getBytes());
        assertEquals(source, buf.toString(Charset.defaultCharset()));
        System.out.println(buf.toString(Charset.defaultCharset()));

        ByteBuffer nioByteBuffer = buf.nioBuffer();
        assertNotNull(nioByteBuffer);
        assertEquals(source, new String(nioByteBuffer.array(),
               nioByteBuffer.arrayOffset(), nioByteBuffer.remaining()));
    }

    @Test
    public void convertJavaBufferToNettyBuffer(){
        ByteBuffer byteBuffer = ByteBuffer.wrap(source.getBytes());
        ByteBuf nettyBuffer = Unpooled.wrappedBuffer(byteBuffer);

        assertEquals(source, nettyBuffer.toString(Charset.defaultCharset()));
    }

}
