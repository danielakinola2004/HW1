//UT-EID=DAA3652_SAO993


public class RunnablePSort implements Runnable {
    private int[] A;
    private int begin, end;
    private boolean increasing;

    RunnablePSort(int[] A, int begin, int end, boolean increasing) {
        this.A = A;
        this.begin = begin;
        this.end = end;
        this.increasing = increasing;
    }

    public static void parallelSort(int[] A, int begin, int end, boolean increasing) {
        RunnablePSort sorter = new RunnablePSort(A, begin, end, increasing);
        Thread t = new Thread(sorter);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        if (end - begin <= 1) {
            return;
        }

        if (end - begin <= 16) {
            insertSort();
            return;
        }

        int mid = begin + (end - begin) / 2;
        int pivot = A[mid];

        int i = begin, j = end - 1;
        while (i <= j) {
            if (increasing) {
                while (i <= j && A[i] < pivot) i++;
                while (i <= j && A[j] > pivot) j--;
            } else {
                while (i <= j && A[i] > pivot) i++;
                while (i <= j && A[j] < pivot) j--;
            }

            if (i <= j) {
                int temp = A[i];
                A[i] = A[j];
                A[j] = temp;
                i++;
                j--;
            }
        }

        Thread leftThread = null;
        Thread rightThread = null;

        if (j > begin) {
            leftThread = new Thread(new RunnablePSort(A, begin, j + 1, increasing));
            leftThread.start();
        }

        if (i < end - 1) {
            rightThread = new Thread(new RunnablePSort(A, i, end, increasing));
            rightThread.start();
        }

        try {
            if (leftThread != null) leftThread.join();
            if (rightThread != null) rightThread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void insertSort() {
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