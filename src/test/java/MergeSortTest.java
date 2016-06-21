import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertArrayEquals;

/**
 * Created by stefano on 15/06/16.
 */
public class MergeSortTest {

    @Test
    public void baseCase() {
        System.out.println("\n=== SINGLE ELEMENT TEST - Start ===");
        int dim = 1;
        Integer[] array = {1};
        Integer[] result = new Integer[array.length];
        Integer[] expected = array.clone();
        Arrays.sort(expected);

        CountDownLatch end = new CountDownLatch(1);
        MergeSort ms = new MergeSort(array, result, end);
        ms.setP(0).setR(dim-1).setS(0);
        new Thread(ms).start();
        try {
            end.await();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        System.out.print("Result: ");
        for(int i = 0; i < result.length; i++)
            System.out.println(" | " + result[i]);
        System.out.print(" |");
        assertArrayEquals(result, expected);
        System.out.println("\n=== SINGLE ELEMENT TEST - End ===");
    }

    @Test
    public void testWithSize2() {
        System.out.println("\n=== TWO ELEMENT TEST - Start ===");
        int dim = 2;
        Integer[] array = {2 ,1};
        Integer[] result = new Integer[array.length];
        Integer[] expected = array.clone();
        Arrays.sort(expected);

        CountDownLatch end = new CountDownLatch(1);
        MergeSort ms = new MergeSort(array, result, end);
        ms.setP(0).setR(dim-1).setS(0);
        new Thread(ms).start();
        try {
            end.await();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        System.out.print("Result: ");
        for(int i = 0; i < result.length; i++)
            System.out.print(" | " + result[i]);
        System.out.print(" |");
        assertArrayEquals(result, expected);
        System.out.println("\n=== TWO ELEMENT TEST - End ===");
    }

    @Test
    public void testWithSize10() {
        System.out.println("\n=== MORE THAN TWO ELEMENTS TEST - Start ===");
        int dim = 10;
        Integer[] array = {2 ,1, -7, 253, 2935, 3, -2358, 8, 478, -34};
        Integer[] result = new Integer[array.length];
        Integer[] expected = array.clone();
        Arrays.sort(expected);

        CountDownLatch end = new CountDownLatch(1);
        MergeSort ms = new MergeSort(array, result, end);
        ms.setP(0).setR(dim-1).setS(0);
        new Thread(ms).start();
        try {
            end.await();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        System.out.print("Result: ");
        for(int i = 0; i < result.length; i++)
            System.out.print(" | " + result[i]);
        System.out.print(" |");
        assertArrayEquals(result, expected);
        System.out.println("\n=== MORE THAN TWO ELEMENTS TEST - End ===");
    }

}
