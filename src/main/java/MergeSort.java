import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by stefano on 14/06/16.
 */
public class MergeSort implements Runnable {

    private Integer[] init;
    private Integer[] result;
    private int p;
    private int r;
    private int s;
    private ExecutorService executor = Executors.newCachedThreadPool();
    private CountDownLatch doneSignal;

    public MergeSort(Integer[] numbers, Integer[] _result, CountDownLatch latch) {
        init = numbers;
        result = _result;
        doneSignal = latch;
        r = numbers.length - 1;
    }

    public MergeSort setP(int _p) {
        p = _p;
        return this;
    }

    public MergeSort setR(int _r) {
        r = _r;
        return this;
    }

    public MergeSort setS(int _s) {
        s = _s;
        return this;
    }

    public void run() {
        sort();
    }

    public void sort() {
        int n = r - p + 1;
        if(n == 1)
            result[s] = init[p];
        else {
            Integer[] T = new Integer[n];
            int q  = (int) Math.floor((r+p)/2); // median value
            int q1 = q - p + 1;                 // "normalized" second starting point
            CountDownLatch myLatch = new CountDownLatch(2);
            MergeSort ms1 = new MergeSort(init, T, myLatch);
            ms1.setP(p).setR(q).setS(0);
            MergeSort ms2 = new MergeSort(init, T, myLatch);
            ms2.setP(q+1).setR(r).setS(q1);
            executor.submit(ms1);
            executor.submit(ms2);
            try {
                myLatch.await();
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
            CountDownLatch mergeLatch = new CountDownLatch(1);
            Merger merger = new Merger(T, result, mergeLatch);
            merger.setP1(0).setR1(q1-1).setP2(q1).setR2(n-1).setP3(s);
            executor.submit(merger);
            try {
                mergeLatch.await();
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
        doneSignal.countDown();
    }

    private class Merger implements Runnable {
        private CountDownLatch doneSignal;
        private int p1 = 0;
        private int r1 = 0;
        private int p2 = 0;
        private int r2 = 0;
        private int p3 = 0;
        private Integer[] src;
        private Integer[] dst;

        public Merger setP1(int p1) {
            this.p1 = p1;
            return this;
        }

        public Merger setR1(int r1) {
            this.r1 = r1;
            return this;
        }

        public Merger setP2(int p2) {
            this.p2 = p2;
            return this;
        }

        public Merger setR2(int r2) {
            this.r2 = r2;
            return this;
        }

        public Merger setP3(int p3) {
            this.p3 = p3;
            return this;
        }

        public Merger(Integer[] _src, Integer[] _dst, CountDownLatch latch) {
            src = _src;
            dst = _dst;
            doneSignal = latch;
        }

        public void run() {
            int n1 = r1 - p1 + 1;
            int n2 = r2 - p2 + 1;
            if(n2 > n1) {
                int aux = n1; n1 = n2;
                aux = p1; p1 = p2; p2 = aux;
                aux = r1; r1 = r2; r2 = aux;
            }
            if(n1 > 0) {
                int q1 = (int) Math.floor((r1+p1)/2);
                int q2 = binarySearch(src[q1], src, p2, r2);
                int q3 = p3 + q1 - p1 + q2 - p2;
                dst[q3] = src[q1];
                CountDownLatch latch = new CountDownLatch(2);
                Merger m1 = new Merger(src, dst, latch);
                m1.setP1(p1).setR1(q1 - 1).setP2(p2).setR2(q2 - 1).setP3(p3);
                Merger m2 = new Merger(src, dst, latch);
                m2.setP1(q1 + 1).setR1(r1).setP2(q2).setR2(r2).setP3(q3 + 1);
                executor.submit(m1);
                executor.submit(m2);
                try {
                    latch.await();
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
            doneSignal.countDown();
        }
    }

    public static int binarySearch(int x, Integer[] t, int p, int r){

        int low = p;
        int high = r+1;

        while(low < high){
            int middle = (int) Math.floor((low+high)/2);
            if( x <= t[middle]){
                high = middle;
            }
            else{

                low = middle +1;
            }

        }

        return high;

    }

}
