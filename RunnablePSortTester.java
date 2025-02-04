import java.util.Arrays;

public class RunnablePSortTester {
    public static void main(String[] args) {
        int[] array = { 10, 2, 35, 4, 22, 13, 47, 1, 18, 25 ,11,12,14,15,16,17,322,232,131,345,6564,24232,1333,32232,};

        // Sorting in increasing order
        System.out.println("Original array: " + Arrays.toString(array));
        RunnablePSort.parallelSort(array, 0, array.length, true);
        System.out.println("Sorted array (increasing): " + Arrays.toString(array));

        // Resetting array for the next test
        array = new int[]{ 10, 2, 35, 4, 22, 13, 47, 1, 18, 25 };

        // Sorting in decreasing order
        RunnablePSort.parallelSort(array, 0, array.length, false);
        System.out.println("Sorted array (decreasing): " + Arrays.toString(array));
    }
}
