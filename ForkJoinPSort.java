//UT-EID=DAA3652

import java.lang.reflect.Array;
import java.util.concurrent.*;

public class ForkJoinPSort extends RecursiveTask<Void>{
    /* Notes:
     * The input array (A) is also the output array,
     * The range to be sorted extends from index begin, inclusive, to index end, exclusive,
     * Sort in increasing order when increasing=true, and decreasing order when increasing=false,
     */
    int [] A;
    int begin, end;
    boolean increasing;
    ForkJoinPSort(int[] A, int begin, int end, boolean increasing){
        A=this.A;
        begin=this.begin;
        end=this.end;
        increasing=this.increasing;
    }
    public static void parallelSort(int[] A, int begin, int end, boolean increasing) {
        // TODO: Implement your parallel sort function using ForkJoinPool
        int processors = Runtime.getRuntime().availableProcessors();
        ForkJoinPool pool = new ForkJoinPool(processors);
        ForkJoinPSort inst = new ForkJoinPSort(A,begin,end,increasing);
        pool.invoke(inst);
    }

    @Override
    protected Void compute() {
        if(A.length>=16){
            ForkJoinPSort l = new ForkJoinPSort(A,begin,end/2,increasing);
            ForkJoinPSort r = new ForkJoinPSort(A,end/2,end,increasing);
            l.fork();
            r.fork();
//            return concatenate(l.join(), r.compute());
        }
        else {
            insertSort();
        }
        return null;
    }

//    public <T> T[] concatenate(T[] a, T[] b) {
//        int aLen = a.length;
//        int bLen = b.length;
//
//        @SuppressWarnings("unchecked")
//        T[] c = (T[]) Array.newInstance(a.getClass().getComponentType(), aLen + bLen);
//        System.arraycopy(a, 0, c, 0, aLen);
//        System.arraycopy(b, 0, c, aLen, bLen);
//
//        return c;
//    }

    public void insertSort(){
        int n = A.length;
        if(increasing) {
            for (int i = 1; i < n; ++i) {
                int key = A[i];
                int j = i - 1;
                while (j >= 0 && A[j] > key) {
                    A[j + 1] = A[j];
                    j = j - 1;
                }
                A[j + 1] = key;
            }
        }
        else{
            for (int i = 1; i < n; ++i) {
                int key = A[i];
                int j = i - 1;
                while (j >= 0 && A[j] < key) {
                    A[j + 1] = A[j];
                    j = j - 1;
                }
                A[j + 1] = key;
            }
        }
    }
}
