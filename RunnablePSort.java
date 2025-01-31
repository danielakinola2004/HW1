//UT-EID=DAA3652

import java.lang.reflect.Array;

public class RunnablePSort implements Runnable{
    /* Notes:
     * The input array (A) is also the output array,
     * The range to be sorted extends from index begin, inclusive, to index end, exclusive,
     * Sort in increasing order when increasing=true, and decreasing order when increasing=false,
     */
    int [] A;
    int begin, end;
    boolean increasing;
    RunnablePSort(int[] A, int begin, int end, boolean increasing){
        A=this.A;
        begin=this.begin;
        end=this.end;
        increasing=this.increasing;
    }
    public static void parallelSort(int[] A, int begin, int end, boolean increasing) {
        // TODO: Implement your parallel sort function using Runnable
        new Thread(new RunnablePSort(A,begin,end,increasing)).start();
    }
    @Override
    public void run() {
        if(A.length>=16){
            Thread l = new Thread( new RunnablePSort(A,begin,end/2,increasing));
            Thread r = new Thread(new RunnablePSort(A,end/2,end,increasing));
            l.start();
            r.start();}
        else {
            insertSort();
        }
    }


//    public int[] concatenate(int[] a, int[] b) {
//        int aLen = a.length;
//        int bLen = b.length;
//
//        @SuppressWarnings("unchecked")
//        int[] c = (int[]) Array.newInstance(a.getClass().getComponentType(), aLen + bLen);
//        System.arraycopy(a, 0, c, 0, aLen);
//        System.arraycopy(b, 0, c, aLen, bLen);
//
//        return c;
//    }

    public void insertSort(){
        int n = end;
        if(increasing) {
            for (int i = begin; i < n; ++i) {
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
            for (int i = begin; i < n; ++i) {
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
