/**
 * Created by stefano on 14/06/16.
 */
public class Fibonacci {




    public static int fibonacciNumber(final int n){

        if (n <= 1){
            return n;
        }
        else{

            final int[] result = {0,0};

            Thread t1 = new Thread(){

                public void run(){
                    result[0] = fibonacciNumber(n-1);

                }
            };

            Thread t2 = new Thread(){
                public void run(){
                    result[1] = fibonacciNumber(n-2);
                }
            };

            t1.start();
            t2.start();

            try{

                t1.join();
                t2.join();

            }
            catch(Exception e){

                System.out.println("Something wrong... stop all threads");
                System.exit(0);

            }

            return result[0]+result[1];
        }
    }
}
