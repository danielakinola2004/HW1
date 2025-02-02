//UT-EID=DAA3652_SAO993

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
        this.A = A;
        this.begin = begin;
        this.end = end;
        this.increasing = increasing;
    }
    public static void parallelSort(int[] A, int begin, int end, boolean increasing) {
        // TODO: Implement your parallel sort function using ForkJoinPool
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        ForkJoinPSort sort = new ForkJoinPSort(A, 0, A.length, increasing);
        forkJoinPool.invoke(sort);

//        int processors = Runtime.getRuntime().availableProcessors();
//        ForkJoinPool pool = new ForkJoinPool(processors);
//        ForkJoinPSort inst = new ForkJoinPSort(A,begin,end,increasing);
//        pool.invoke(inst);
    }

    @Override
    protected Void compute() {
        if(this.end - this.begin > 16) {
            int mid = begin + (end - begin)/2;
            ForkJoinPSort l = new ForkJoinPSort(A,begin,mid,increasing);
            ForkJoinPSort r = new ForkJoinPSort(A,mid,end,increasing);
            l.fork();
            r.fork();

            invokeAll(l, r);
            merge(this.begin, mid, this.end);
        } else {
            insertSort();
        }
        return null;
    }

    private void merge(int begin, int mid, int end) {
        int[] temp = new int[end - begin];
        int i = begin, j = mid, k = 0;

        while (i < mid && j < end) {
            if (increasing ? A[i] <= A[j] : A[i] >= A[j]) {
                temp[k++] = A[i++];
            } else {
                temp[k++] = A[j++];
            }
        }

        while (i < mid) {
            temp[k++] = A[i++];
        }
        while (j < end) {
            temp[k++] = A[j++];
        }

        // Copy back to original array
        System.arraycopy(temp, 0, A, begin, temp.length);
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

    public void insertSort() {
        int n = this.end;
        for (int i = this.begin; i < n; i++) {
            int key = A[i];
            int j = i - 1;
            if (increasing) {
                while (j >= 0 && A[j] > key) {
                    A[j + 1] = A[j];
                    j--;
                }
            } else {
                while (j >= 0 && A[j] < key) {
                    A[j + 1] = A[j];
                    j--;
                }
            }
            A[j + 1] = key;
        }
    }
}
