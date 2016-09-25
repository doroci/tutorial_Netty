import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by lee on 2016. 9. 25..
 */
public class ReadWriteByteBufferByNettyTest {

    private void testBuffer(ByteBuf buf, boolean isDirect){
        assertEquals(11, buf.capacity());
        assertEquals(isDirect, buf.isDirect());

        buf.writeInt(65537);
        assertEquals(4, buf.readableBytes());
        assertEquals(7, buf.writableBytes());

        assertEquals(1, buf.readShort());
        assertEquals(2, buf.readableBytes());
        assertEquals(7, buf.writableBytes());

        assertEquals(true, buf.isReadable());

        buf.clear();

        assertEquals(0, buf.readableBytes());
        assertEquals(11, buf.writableBytes());
    }

    @Test
    public void createUnpooledHeapBufferTest(){
        ByteBuf buf = Unpooled.buffer(11);
        testBuffer(buf,false);
    }
    @Test
    public void createUnpooledDirectBufferTest(){
        ByteBuf buf = Unpooled.directBuffer(11);
        testBuffer(buf, true);
    }
    @Test
    public void createPooledHeapBufferTest(){
        ByteBuf buf = PooledByteBufAllocator.DEFAULT.heapBuffer(11);
        testBuffer(buf, false);
    }
    @Test
    public void createPooledDirectBufferTest(){
        ByteBuf buf = PooledByteBufAllocator.DEFAULT.directBuffer(11);
        testBuffer(buf, true);
    }
    @Test
    public void createDefualtBufferTest(){
        ByteBuf buf = PooledByteBufAllocator.DEFAULT.buffer();
        assertEquals(256, buf.writableBytes());
    }
}
