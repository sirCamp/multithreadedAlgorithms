import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by stefano on 14/06/16.
 */
public class FibonacciTest {

    @Test
    public void testFirstFibonacciNumber() {


        assertEquals(1,Fibonacci.fibonacciNumber(1));

    }

    @Test
    public void testSecondFibonacciNumber() {

        assertEquals(1,Fibonacci.fibonacciNumber(2));

    }

    @Test
    public void testFinfthFibonacciNumber() {

        assertEquals(5,Fibonacci.fibonacciNumber(5));

    }

    @Test
    public void testTwelfthFibonacciNumber() {

        assertEquals(144,Fibonacci.fibonacciNumber(12));

    }
}
