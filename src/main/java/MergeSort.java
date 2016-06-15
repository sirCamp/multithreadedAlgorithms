import java.util.Arrays;

/**
 * Created by stefano on 14/06/16.
 */
public class MergeSort {



    public static void main(String[] args)
    {
        final Integer[] a = {2, 6, 3, 5, 1};
        final Integer[] b = new Integer[a.length];
        pMergeSort(a,0,a.length-1,b,0,"ROOT");

        /*Thread master = new Thread(){
            public void run(){
                pMergeSort(a, 1, a.length-1,b, 0,"ROOT");
            }
        };

        master.start();
        try {
            master.join();
            System.out.println("FINE: " +Arrays.toString(b));
        }
        catch (Exception e){

        }*/
    }


    public static int binarySearch(int x, Integer[] t, int p, int r){

        int low =p;
        int high = Math.max(p,r+1);

        while( low < high ){

            int middle = (int)Math.floor((low+high)/2);
            if( x <= t[middle]){

                high = middle;
            }
            else{

                low = middle +1;
            }

        }

        return high;

    }


    public static void pMerge(final Integer[] t, int p1_a,  int r1_a,  int p2_a,  int r2_a, final Integer[] a,final int p3){

        int n1 = r1_a-p1_a+1;
        int n2 = r2_a-p2_a+1;


        if (n1 < n2){

            int tmp_p = p1_a;
            p1_a=p2_a;
            p2_a=tmp_p;

            int tmp_r1 = r1_a;
            r1_a=r2_a;
            r2_a=r1_a;

            int tmp_n = n1;
            n1=n2;
            n2=tmp_n;
        }

        final int r1 = r1_a;
        final int r2 = r2_a;
        final int p1 = p1_a;
        final int p2 = p2_a;


        if(n1 == 0){
            return;
        }
        else{


            System.out.println("INVOCO IL MERGE: "+Arrays.toString(a));
            final int q1 = (int)Math.floor((p1+r1)/2);

            final int q2 = MergeSort.binarySearch(t[q1],t,p2,r2);
            final int q3 = p3+(q1-p1)+(q2-p2);
            a[q3] = t[q1];



            Thread t1 = new Thread(){
                public void run(){

                    pMerge(t,p1,q1-1,p2,q2-1,a,p3);
                }
            };

            Thread t2 = new Thread(){
                public void run(){

                    pMerge(t,q1+1,r1,q2,r2,a,q3+1);
                }
            };

            t1.start();
            t2.start();

            try{

                t1.join();
                t2.join();
            }
            catch(Exception e){

            }



        }

    }


    public static void pMergeSort(final Integer[] a, final int p, final int r,final Integer[] b, final int s, String kind){
        int n = r-p+1;

        if(n==1){

            System.out.println("SONO: "+kind+" s :"+s+" p: "+p+" b: "+b.length+" a: "+a.length+" r-p+1: "+(r-p+1)+" r:"+r+" q: "+p+" plus: "+1);

           int news = s;
           if(s == b.length) {
               news = s - 1;
           }

           b[news] = a[p];
           System.out.println("b[s]: "+b[news]);
           System.out.println(Arrays.toString(b));

        }
        else{
            final Integer[] t = new Integer[n];
            for(int i=0; i<n; i++){
                t[i] = 0;
            }
            final int q = (int)Math.floor((p+r)/2);
            final int q1 = q-p+1;



            Thread t1 = new Thread(){
                public void run(){

                    System.out.println("1: "+Arrays.toString(a)+" "+(p)+" "+q+" "+Arrays.toString(t)+" "+0);
                    pMergeSort(a,p,q,t,1, "T1");
                }
            };

            Thread t2 = new Thread(){
                public void run(){


                    System.out.println("2: "+Arrays.toString(a)+" "+(q+1)+" "+r+" "+Arrays.toString(t)+" "+(q1+1));
                    pMergeSort(a,q+1,r,t,q1+1,"T2");

                }
            };

            t1.start();
            t2.start();

            try{

                t1.join();
                t2.join();
                pMerge(t,1,q1,q1+1,n,b,s);
                System.out.println(Arrays.toString(b));
            }
            catch(Exception e){

            }

            return;

        }
    }

    /*public static void mergeSort(final Integer[] a,final  int left, final int right){

        if(left < right){

            final int middle = (int)Math.floor((left+right)/2);

            Thread t1 = new Thread(){

                public void run(){
                    mergeSort(a,left,middle);
                }
            };

            Thread t2 = new Thread(){

                public void run(){
                    mergeSort(a,middle+1,right);
                }
            };

            t1.start();
            t2.start();

            try{

                t1.join();
                t2.join();
            }
            catch (Exception e){

                System.out.println("Something during margeSort was wrong... stopping all");
                System.exit(1);
            }


            merge(a,left,middle,right);
        }

    }

    private static void merge(Integer[] a,int left, int middle, int right )
    {
        int i = left;
        int j = middle + 1;
        int k = 0;
        Integer[] b = new Integer[right-left+1];

        while (i <= middle && j <= right) {
            if(a[i]<=a[j]) {
                b[k] = a[i];
                i = i + 1;
            }    
            else {
                b[k]=a[j];
                j=j + 1;

            }
            k=k + 1;
        }    

        while(i <= middle) {
            b[k] = a[i];
            i = i + 1;
            k = k + 1;
        }

        while( j <= right ) {
            b[k] = a[j];
            j = j + 1;
            k = k + 1;
        }

        for( k = left; k <= right; k++){
            a[k] = b[k - left];
        }
    }*/
}
