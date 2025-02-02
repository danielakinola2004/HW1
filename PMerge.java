//UT-EID=

import java.util.*;
import java.util.concurrent.*;

public class PMerge implements Runnable{
    /* Notes:
     * Arrays A and B are sorted in the ascending order
     * These arrays may have different sizes.
     * Array C is the merged array sorted in the descending order
     */
    static ArrayList<Integer> arr;
    int[] A , B, C;
    int iA, iB, iC;
    int numThread;

    PMerge(int[] A, int[] B, int[] C) {
        this.A = A;
        this.C = C;
        this.B = B;
        iA = A.length - 1;
        iB = B.length-1;
        iC = 0;
    }

    public static void parallelMerge(int[] A, int[] B, int[] C, int numThreads) {
        // TODO: Implement your parallel merge function
        arr = new ArrayList<>();
        for(int i=0; i<numThreads; i++) {
            new Thread(new PMerge(A,B,C)).start();
        }
    }

    @Override
    public void run() {
        while (iA >= 0 || iB >= 0) {
            if (iA < 0) {
                add(false);
            } else if (iB < 0) {
                add(true);
            } else if (A[iA] > B[iB]) {
                add(true);
            } else if (A[iA] <= B[iB]) {
                add(false);
            }
        }
    }

    public synchronized void add(boolean isA) {
        if (isA) {
            C[iC] = A[iA];
            iA--;
        } else {
            C[iC] = B[iB];
            iB--;
        }
        iC++;
    }
}
