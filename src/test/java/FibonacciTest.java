import jdk.nashorn.internal.runtime.linker.JavaAdapterFactory;
import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.assertEquals;

/**
 * Created by stefano on 14/06/16.
 */
public class FibonacciTest {

    @Test
    public void testFibonacciOnZero() {
        Fibonacci fib = new Fibonacci();
        assertEquals(BigInteger.ZERO, fib.fibonacciNumber(0));

    }

    @Test
    public void testFibonacciOnOne() {
        Fibonacci fib = new Fibonacci();
        assertEquals(BigInteger.ONE, fib.fibonacciNumber(1));

    }

    @Test
    public void testFibonacciOnTwo() {
        Fibonacci fib = new Fibonacci();
        assertEquals(BigInteger.ONE, fib.fibonacciNumber(2));

    }

    @Test
    public void testFibonacciOnFive() {
        Fibonacci fib = new Fibonacci();
        BigInteger expected = BigInteger.valueOf(5);
        assertEquals(expected, fib.fibonacciNumber(5));

    }

    @Test
    public void testFibonacciOnTwelve() {
        Fibonacci fib = new Fibonacci();
        BigInteger expected = BigInteger.valueOf(144);
        assertEquals(expected, fib.fibonacciNumber(12));
    }

    @Test
    public void testFibonacciSpeed() {
        Fibonacci fib = new Fibonacci();
        long start = System.currentTimeMillis();
        fib.fibonacciNumber(30);
        long stop = System.currentTimeMillis();
        System.out.println("Time on input 25: " + (stop-start));
    }
}
