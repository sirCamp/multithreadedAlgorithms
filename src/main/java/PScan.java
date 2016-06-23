import java.util.concurrent.*;

/**
 *
 */
public class PScan {
    private int dim;
    private Integer[] X;
    private Integer[] Y;
    private Integer[] T;

    private ExecutorService service = Executors.newCachedThreadPool();

    public PScan(Integer[] array) {
        X = array.clone();
        dim = X.length;
        Y = new Integer[dim];
        T = new Integer[dim];
    }

    public Integer[] doScan() {
        if(X == null)
            throw new IllegalArgumentException("Not initialized yet");
        Y[0] = X[0];
        if(dim > 1) {
            CountDownLatch endUp = new CountDownLatch(1);
            PScanUp pScanUp = new PScanUp(X, T, 1, dim-1).withLatch(endUp);
            service.submit(pScanUp);
            try {
                endUp.await();
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
            CountDownLatch endDown = new CountDownLatch(1);
            PScanDown pScanDown = new PScanDown(X[0], X, T, Y, 1, dim-1).withLatch(endDown);
            service.submit(pScanDown);
            try {
                endDown.await();
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
        return Y;
    }

    private class PScanUp implements Runnable {
        private int i;
        private int j;
        private Integer[] X;
        private Integer[] T;
        private Integer result;
        private CountDownLatch latch;

        public PScanUp(Integer[] _X, Integer[] _T, int _i, int _j) {
            i = _i;
            j = _j;
            X = _X;
            T = _T;
        }

        public PScanUp withLatch(CountDownLatch latch) {
            this.latch = latch;
            return this;
        }

        public Integer getResult() {
            return result;
        }

        public void run() {
            if(i == j) {
                result = X[i];
                latch.countDown();
                return;
            }
            int k = (int) Math.floor((i+j)/2);
            CountDownLatch children = new CountDownLatch(2);
            PScanUp lowerHalf = new PScanUp(X,T,i,k).withLatch(children);
            PScanUp higherHalf = new PScanUp(X,T,k+1,j).withLatch(children);
            service.submit(lowerHalf);
            service.submit(higherHalf);
            try {
                children.await();
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
            T[k] = lowerHalf.getResult();
            int r = higherHalf.getResult();
            result = T[k] + r;
            latch.countDown();
        }
    }

    private class PScanDown implements Runnable {
        private int v;
        private int i;
        private int j;
        private Integer[] X;
        private Integer[] T;
        private Integer[] Y;
        private CountDownLatch latch;

        public PScanDown(Integer _v, Integer[] _X, Integer[] _T, Integer[] _Y, int _i, int _j) {
            v = _v;
            i = _i;
            j = _j;
            X = _X;
            T = _T;
            Y = _Y;
        }

        public PScanDown withLatch(CountDownLatch latch) {
            this.latch = latch;
            return this;
        }

        public void run() {
            if(i == j) {
                Y[i] = v + X[i];
                latch.countDown();
                return;
            }
            int k = (int) Math.floor((i+j)/2);
            CountDownLatch children = new CountDownLatch(2);
            PScanDown lowerHalf = new PScanDown(v,X,T,Y,i,k).withLatch(children);
            PScanDown higherHalf = new PScanDown(v+T[k],X,T,Y,k+1,j).withLatch(children);
            service.submit(lowerHalf);
            service.submit(higherHalf);
            try {
                children.await();
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
            latch.countDown();
        }
    }
}
