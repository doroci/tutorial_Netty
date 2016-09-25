import org.junit.Before;
import org.junit.Test;

import java.nio.ByteBuffer;

import static org.junit.Assert.assertEquals;

/**
 * Created by lee on 2016. 9. 24..
 */
public class ByteBufferTest3 {

    @Test
    public void test(){

        ByteBuffer firstBuffer = ByteBuffer.allocate(11);
        System.out.println("초기 상태 :" +firstBuffer);

        firstBuffer.put((byte) 1);
        firstBuffer.put((byte) 2);
        assertEquals(2,firstBuffer.position());

        System.out.println("중간 상태 :" +firstBuffer.get());

        firstBuffer.rewind();
        assertEquals(0, firstBuffer.position());

        assertEquals(1, firstBuffer.get());
        assertEquals(1, firstBuffer.position());

        System.out.println(firstBuffer);
    }



}
