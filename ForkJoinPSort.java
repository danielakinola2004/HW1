import java.util.concurrent.*;

public class ForkJoinPSort extends RecursiveTask<Void> {
    int[] A;
    int begin, end;
    boolean increasing;

    ForkJoinPSort(int[] A, int begin, int end, boolean increasing) {
        this.A = A;
        this.begin = begin;
        this.end = end;
        this.increasing = increasing;
    }

    public static void parallelSort(int[] A, int begin, int end, boolean increasing) {
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        ForkJoinPSort sort = new ForkJoinPSort(A, begin, end, increasing);
        forkJoinPool.invoke(sort);
    }

    @Override
    protected Void compute() {
        if (this.end - this.begin > 16) {
            int mid = begin + (end - begin) / 2;
            ForkJoinPSort l = new ForkJoinPSort(A, begin, mid, increasing);
            ForkJoinPSort r = new ForkJoinPSort(A, mid, end, increasing);
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

        System.arraycopy(temp, 0, A, begin, temp.length);
    }

    private void insertSort() {
        for (int i = this.begin + 1; i < this.end; i++) {
            int key = A[i];
            int j = i - 1;
            if (increasing) {
                while (j >= this.begin && A[j] > key) {
                    A[j + 1] = A[j];
                    j--;
                }
            } else {
                while (j >= this.begin && A[j] < key) {
                    A[j + 1] = A[j];
                    j--;
                }
            }
            A[j + 1] = key;
        }
    }
}
