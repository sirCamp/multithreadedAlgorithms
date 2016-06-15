import java.math.BigInteger;
import java.util.concurrent.*;

/**
 * Created by stefano on 14/06/16.
 */
public class Fibonacci {


    private ExecutorService executor = Executors.newCachedThreadPool();

    private class FibonacciComputer implements Runnable {
        private BigInteger number;
        private BigInteger result;
        private CountDownLatch doneSignal;

        public FibonacciComputer(BigInteger _number, CountDownLatch latch) {
            number = _number;
            doneSignal = latch;
        }

        public BigInteger getResult() {
            return result;
        }

        public void run() {
            if(number.compareTo(BigInteger.ONE) <= 0) {
                result = number;
                doneSignal.countDown();
            } else {
                BigInteger prevN = number.subtract(BigInteger.ONE);
                BigInteger prevPrevN = prevN.subtract(BigInteger.ONE);
                CountDownLatch myLatch = new CountDownLatch(2);
                FibonacciComputer computer1 = new FibonacciComputer(prevN, myLatch);
                FibonacciComputer computer2 = new FibonacciComputer(prevPrevN, myLatch);
                executor.submit(computer1);
                executor.submit(computer2);
                try {
                    myLatch.await();
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
                BigInteger result1 = computer1.getResult();
                BigInteger result2 = computer2.getResult();
                result = result1.add(result2);
                doneSignal.countDown();
            }
        }
    }

    public BigInteger fibonacciNumber(final int n){
        BigInteger number = BigInteger.valueOf(n);
        CountDownLatch end = new CountDownLatch(1);
        FibonacciComputer computer = new FibonacciComputer(number, end);
        executor.submit(computer);
        try {
            end.await();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        return computer.getResult();
    }
}
