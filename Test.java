import java.util.Arrays;
import java.util.Random;

public class Test {
    public static void main(String[] args) {
        int size = 1600;
        Random rand = new Random(42); // Fixed seed for reproducibility
        int[] A = new int[size];
        for (int i = 0; i < size; i++) {
            A[i] = rand.nextInt(100);
        }
        System.out.println(Arrays.toString(A));
        ForkJoinPSort.parallelSort(A, 0, A.length, true);
//        RunnablePSort.parallelSort(A, 0, A.length, true);
        System.out.println(Arrays.toString(A));
    }
}