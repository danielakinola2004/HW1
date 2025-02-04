import java.util.Arrays;

public class ForkJoinPSortTester {
    public static void main(String[] args) {
        int[] array = { 10, 2, 35, 4, 22, 13, 47, 1, 18, 25 };

        // Sorting in increasing order
        System.out.println("Original array: " + Arrays.toString(array));
        ForkJoinPSort.parallelSort(array, 0, array.length, true);
        System.out.println("Sorted array (increasing): " + Arrays.toString(array));

        // Resetting array for the next test
        array = new int[]{ 10, 2, 35, 4, 22, 13, 47, 1, 18, 25 };

        // Sorting in decreasing order
        ForkJoinPSort.parallelSort(array, 0, array.length, false);
        System.out.println("Sorted array (decreasing): " + Arrays.toString(array));
    }
}
