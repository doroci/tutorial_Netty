import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by lee on 2016. 9. 25..
 */
public class UnsignedByteBufferTest {

    final String source = "Hello world";

    @Test
    public void unsignedBufferToJavaBuffer(){
        ByteBuf buf = Unpooled.buffer(1);
        buf.writeShort(-1);

        assertEquals(65535, buf.getUnsignedShort(0));
    }


}
