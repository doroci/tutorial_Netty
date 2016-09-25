import org.junit.Test;

import java.nio.ByteBuffer;

import static org.junit.Assert.assertEquals;

/**
 * Created by lee on 2016. 9. 24..
 */
public class WriteByteBufferTest {

    @Test
    public void writeTest(){
        ByteBuffer firstBuffer = ByteBuffer.allocateDirect(11);
        assertEquals(0, firstBuffer.position());
        assertEquals(11, firstBuffer.limit());

        firstBuffer.put((byte) 1);
        firstBuffer.put((byte) 2);
        firstBuffer.put((byte) 3);
        firstBuffer.put((byte) 4);
        assertEquals(4, firstBuffer.position());
        assertEquals(11, firstBuffer.limit());

        firstBuffer.flip();
        assertEquals(0, firstBuffer.position());
        assertEquals(4, firstBuffer.limit());
    }
}
