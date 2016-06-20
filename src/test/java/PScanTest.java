import static org.junit.Assert.*;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by nate on 19/06/16.
 */
public class PScanTest {

    @Test
    public void edgeCaseTest() {
        System.out.println("\n=== EDGE CASE - Start ===");
        Integer[] array    = {-6};
        Integer[] expected = {-6};
        PScan pScan = new PScan(array);
        Integer[] result = pScan.doScan();
        assertArrayEquals(result, expected);
        System.out.println("=== EDGE CASE - End ===\n");
    }

    @Test
    public void basicDoScanTest() {
        System.out.println("\n=== BASIC TEST - Start ===");
        Integer[] array    = {1,2,3,4,5};
        Integer[] expected = {1,3,6,10,15};
        PScan pScan = new PScan(array);
        Integer[] result = pScan.doScan();
        assertArrayEquals(result, expected);
        System.out.println("=== BASIC TEST - End ===\n");
    }

    @Test
    public void tougherTest() {
        System.out.println("\n=== TOUGHER TEST - Start ===");
        Integer[] array    = {1,5,3,-26,5,98,123,636,26};
        Integer[] expected = {1,6,9,-17,-12,86,209,845,871};
        PScan pScan = new PScan(array);
        Integer[] result = pScan.doScan();
        assertArrayEquals(result, expected);
        System.out.println("=== TOUGHER TEST - End ===\n");
    }
}
