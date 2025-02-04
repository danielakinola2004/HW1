//UT-EID=DAA3652_SAO993

import java.util.*;

public class PMerge implements Runnable {
    /* Notes:
     * Arrays A and B are sorted in the ascending order
     * These arrays may have different sizes.
     * Array C is the merged array sorted in the descending order
     */
    int[] A, B, C;
    int end, start;
    boolean isA;

    PMerge(int[] A, int[] B, int[] C, int start, int end, boolean isA) {
        this.A = A;
        this.B = B;
        this.C = C;
        this.end = end;
        this.start = start;
        this.isA = isA;
    }

    public static void parallelMerge(int[] A, int[] B, int[] C, int numThreads) {
        // TODO: Implement your parallel merge function

        // Merge A to C
        numThreads = Math.min(numThreads, A.length);
        int chunkSize, remainder;
        int currentStart = 0;
        Thread[] threads;
        if (numThreads != 0) {
            chunkSize = A.length / numThreads;
            remainder = A.length % numThreads;

            threads = new Thread[numThreads];
            makethread(A, B, C, numThreads, chunkSize, remainder, threads, currentStart, true);
        }

        // Merge B to C
        numThreads = Math.min(numThreads, B.length);
        if (numThreads != 0) {
            chunkSize = B.length / numThreads;
            remainder = B.length % numThreads;

            threads = new Thread[numThreads];
            makethread(A, B, C, numThreads, chunkSize, remainder, threads, currentStart, false);
        }
    }

    private static void makethread(int[] A, int[] B, int[] C, int numThreads, int chunkSize, int remainder, Thread[] threads, int currentStart, boolean isA) {
        for (int i = 0; i < numThreads; i++) {
            int currentEnd = currentStart + chunkSize + (i < remainder ? 1 : 0);
            threads[i] = new Thread(new PMerge(A, B, C, currentStart, currentEnd, isA));
            threads[i].start();
            currentStart = currentEnd;
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        for (int i = this.start; i < this.end; i++) {
            int curr, pos, posC;
            if (isA)
                curr = A[i];
            else
                curr = B[i];

            if (isA)
                pos = binarySearchGreater(curr, B);
            else
                pos = binarySearchGreater(curr, A);

            if (pos == -1) {
                if (isA)
                    posC = i + B.length;
                else
                    posC = i + A.length;
            } else {
                if (isA)
                    posC = i + pos;
                else {
                    if (pos - 1 >= 0 && A[pos - 1] == curr)
                        posC = i + pos - 1;
                    else
                        posC = i + pos;
                }
            }

            synchronized (C) {
                C[C.length - posC - 1] = curr;
            }
        }
    }

    private int binarySearchGreater(int target, int[] arr) {
        int left = 0;
        int right = arr.length - 1;
        int result = -1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] > target) {
                result = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return result;
    }
}
