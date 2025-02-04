import java.lang.reflect.Array;
import java.util.Arrays;

class PMerge implements Runnable{
    /* Notes:
     * The input array (A) is also the output array,
     * The range to be sorted extends from index begin, inclusive, to index end, exclusive,
     * Sort in increasing order when increasing=true, and decreasing order when increasing=false,
     */
    int [] A;
    int begin, end;
    boolean increasing;
    PMerge(int[] A, int begin, int end, boolean increasing) {
        this.A = A;
        this.begin = begin;
        this.end = end;
        this.increasing = increasing;
    }
    public static void parallelSort(int[] A, int begin, int end, boolean increasing) {
        // TODO: Implement your parallel sort function using Runnable
        RunnablePSort sorter = new RunnablePSort(A, begin, end, increasing);
        Thread t = new Thread(sorter);
        t.start();
        /*
        Have the join function, so the original thread can wait on its other
        threads to finish before it exits the function. If this join was not included,
        thread will lead to unexpected behaviour because sorter might finish before T,
        or T might finish before sorter.
        */
        try {
            t.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void run() {
        int mid = begin + (end - begin) / 2;
        if(this.end - this.begin > 16) {
            Thread l = new Thread(new RunnablePSort(A, begin,mid, increasing));
            Thread r = new Thread(new RunnablePSort(A,mid, end, increasing));
            l.start();
            r.start();
            try {
                l.join();
                r.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } else {
            insertSort();
        }
        merge(this.begin, mid, this.end);
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
        System.arraycopy(temp, 0, A, begin, temp.length);
    }

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