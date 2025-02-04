public class RunnablePSort implements Runnable {
    int[] A;
    int begin, end;
    boolean increasing;

    RunnablePSort(int[] A, int begin, int end, boolean increasing) {
        this.A = A;
        this.begin = begin;
        this.end = end;
        this.increasing = increasing;
    }

    public static void parallelSort(int[] A, int begin, int end, boolean increasing) {
        Thread sortThread = new Thread(new RunnablePSort(A, begin, end, increasing));
        sortThread.start();
        try {
            sortThread.join();  // Ensure main thread waits for sorting to complete
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        if (end - begin < 16) {
            insertSort(begin, end);
        } else {
            int pivotIndex = partition(begin, end);
            Thread l = new Thread(new RunnablePSort(A, begin, pivotIndex, increasing));
            Thread r = new Thread(new RunnablePSort(A, pivotIndex + 1, end, increasing));
            l.start();
            r.start();
            try {
                l.join();
                r.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private int partition(int begin, int end) {
        int pivot = A[end - 1];
        int i = begin - 1;
        for (int j = begin; j < end - 1; j++) {
            if (increasing ? A[j] <= pivot : A[j] >= pivot) {
                i++;
                swap(i, j);
            }
        }
        swap(i + 1, end - 1);
        return i + 1;
    }

    private void swap(int i, int j) {
        int temp = A[i];
        A[i] = A[j];
        A[j] = temp;
    }

    private void insertSort(int begin, int end) {
        for (int i = begin + 1; i < end; i++) {
            int key = A[i];
            int j = i - 1;
            if (increasing) {
                while (j >= begin && A[j] > key) {
                    A[j + 1] = A[j];
                    j--;
                }
            } else {
                while (j >= begin && A[j] < key) {
                    A[j + 1] = A[j];
                    j--;
                }
            }
            A[j + 1] = key;
        }
    }
}
