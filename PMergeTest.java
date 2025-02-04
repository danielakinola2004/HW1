////package HW1;
//
//import org.junit.Test;
//import static org.junit.Assert.*;
//        import java.util.Arrays;
//import java.util.Random;
//
//public class PMergeTest {
//
//    @Test
//    public void testEmptyArrays() {
//        int[] A = {};
//        int[] B = {};
//        int[] C = new int[0];
//        PMerge.parallelSort(A, B, C, 4);
//        assertArrayEquals(new int[0], C);
//    }
//
//    @Test
//    public void testOneEmptyArray() {
//        int[] A = {5, 3, 1};
//        int[] B = {};
//        int[] C = new int[3];
//        PMerge.parallelSort(A, B, C, 4);
//        assertArrayEquals(new int[]{5, 3, 1}, C);
//
//        // Test with first array empty
//        A = new int[]{};
//        B = new int[]{6, 4, 2};
//        C = new int[3];
//        PMerge.pMerge(A, B, C, 4);
//        assertArrayEquals(new int[]{6, 4, 2}, C);
//    }
//
//    @Test
//    public void testSingleElementArrays() {
//        int[] A = {1};
//        int[] B = {2};
//        int[] C = new int[2];
//        PMerge.pMerge(A, B, C, 4);
//        assertArrayEquals(new int[]{2, 1}, C);
//    }
//
//    @Test
//    public void testDuplicateElements() {
//        int[] A = {5, 3, 3, 1};
//        int[] B = {4, 3, 3, 2};
//        int[] C = new int[8];
//        PMerge.pMerge(A, B, C, 4);
//        assertArrayEquals(new int[]{5, 4, 3, 3, 3, 3, 2, 1}, C);
//    }
//
//    @Test
//    public void testUnequalSizeArrays() {
//        int[] A = {7, 5, 3};
//        int[] B = {6, 4, 2, 1};
//        int[] C = new int[7];
//        PMerge.pMerge(A, B, C, 4);
//        assertArrayEquals(new int[]{7, 6, 5, 4, 3, 2, 1}, C);
//    }
//
//    @Test
//    public void testLargeArrays() {
//        int[] A = new int[10000];
//        int[] B = new int[10000];
//        for (int i = 0; i < 10000; i++) {
//            A[i] = 20000 - (2 * i);
//            B[i] = 19999 - (2 * i);
//        }
//        int[] C = new int[20000];
//        PMerge.pMerge(A, B, C, 8);
//
//        // Verify sorting
//        for (int i = 0; i < C.length - 1) {
//            assertTrue("Array not sorted in descending order at index " + i,
//                    C[i] >= C[i + 1]);
//        }
//    }
//
//    @Test
//    public void testDifferentThreadCounts() {
//        int[] A = {5, 3, 1};
//        int[] B = {6, 4, 2};
//        int[] expected = {6, 5, 4, 3, 2, 1};
//
//        // Test with 1 thread
//        int[] C1 = new int[6];
//        PMerge.pMerge(A, B, C1, 1);
//        assertArrayEquals(expected, C1);
//
//        // Test with more threads than elements
//        int[] C2 = new int[6];
//        PMerge.pMerge(A, B, C2, 10);
//        assertArrayEquals(expected, C2);
//    }
//
//    @Test
//    public void testStressWithRandomData() {
//        Random rand = new Random(42); // Fixed seed for reproducibility
//        int size = 10000;
//        int[] A = new int[size];
//        int[] B = new int[size];
//
//        // Generate sorted arrays
//        for (int i = 0; i < size; i++) {
//            A[i] = rand.nextInt(1000000);
//            B[i] = rand.nextInt(1000000);
//        }
//        Arrays.sort(A);
//        Arrays.sort(B);
//
//        // Reverse arrays to make them descending
//        for (int i = 0; i < size/2; i++) {
//            int temp = A[i];
//            A[i] = A[size-1-i];
//            A[size-1-i] = temp;
//
//            temp = B[i];
//            B[i] = B[size-1-i];
//            B[size-1-i] = temp;
//        }
//
//        int[] C = new int[size * 2];
//        PMerge.pMerge(A, B, C, 4);
//
//        // Verify sorting
//        for (int i = 0; i < C.length - 1; i++) {
//            assertTrue("Array not sorted in descending order at index " + i,
//                    C[i] >= C[i + 1]);
//        }
//    }
//}