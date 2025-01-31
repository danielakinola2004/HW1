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
    int[] A , B, C; int numThread;

    PMerge(int[] A, int[] B, int[] C){
        A=this.A;
        C=this.C;
        B=this.B;
    }
    public static void parallelMerge(int[] A, int[] B, int[] C, int numThreads) {
        // TODO: Implement your parallel merge function
        arr = new ArrayList<>();
        for(int i=0; i<numThreads; i++){
            new Thread(new PMerge(A,B,C)).start();
        }
    }

    @Override
    public void run() {

    }
}
