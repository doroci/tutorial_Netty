import io.netty.buffer.*;
import org.junit.Test;


import static org.junit.Assert.assertEquals;

/**
 * Created by lee on 2016. 9. 25..
 */
public class CreateByteBufferByNettyTest {

    private void testBuffer(ByteBuf buf, boolean isDirect){

        assertEquals(11, buf.capacity());
        assertEquals(isDirect, buf.isDirect());
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
