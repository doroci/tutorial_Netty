import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.junit.Test;

import java.nio.ByteOrder;

import static org.junit.Assert.assertEquals;

/**
 * Created by lee on 2016. 9. 25..
 */
public class OrderedByteBufferTest {

    @Test
    public void pooledHeapBufferTest(){
        ByteBuf buf = Unpooled.buffer();
        assertEquals(ByteOrder.BIG_ENDIAN, buf.order());

        buf.writeShort(1);

        buf.markReaderIndex();
        assertEquals(1, buf.readShort());

        buf.resetReaderIndex();

        ByteBuf lettleEndianBuf = buf.order(ByteOrder.LITTLE_ENDIAN);
        assertEquals(256, lettleEndianBuf.readShort());
    }
}
