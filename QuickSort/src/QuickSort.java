import java.util.Arrays;
import java.util.Random;

public class QuickSort {

    private QuickSort() {}

    public static <E extends Comparable<E>> void sort(E[] arr) {

        // loop invariant: arr[l + 1, j] < v ; arr[j + 1, i - 1] > v
        sort(arr, 0, arr.length - 1, 0);
    }

    public static <E extends Comparable<E>> void sort2(E[] arr) {

        // Only create one instance to prevent allocating memory spaces repeatedly
        Random rnd = new Random();

        // loop invariant: arr[l + 1, j] < v ; arr[j + 1, i - 1] > v
        sort2(arr, 0, arr.length - 1, rnd, 0);
    }

    private static <E extends Comparable<E>> void sort2(E[] arr, int l, int r, Random rnd, int depth){

        String depthString = generateDepthString(depth);
        System.out.print(depthString);
        System.out.printf("QuickSort[%d, %d]\n", l, r);

        if (l >= r) return;

        // 使用 Insertion Sort 优化
//        if(r - l <= 15){
//            InsertionSort.sort(arr, l, r);
//            return; // 注意，这里要 return！
//        }

        int p = partition2(arr, l, r, rnd);

        System.out.print(depthString);
        System.out.printf("partition arr[%d, %d] and arr[%d, %d]", l, p, p + 1, r);

        sort2(arr, l, p - 1, rnd, depth + 1);
        sort2(arr, p + 1, r, rnd, depth + 1);

        System.out.print(depthString);
        System.out.printf("after QuickSort arr[%d, %d] :", l, r);
        for(E e: arr)
            System.out.print(e + " ");
        System.out.println();
    }

    private static <E extends Comparable<E>> void sort(E[] arr, int l, int r, int depth) {

        String depthString = generateDepthString(depth);
        System.out.print(depthString);
        System.out.printf("QuickSort[%d, %d]\n", l, r);

        // check for breakout
        if (l >= r) return;

        int p = partition(arr, l, r);

        System.out.print(depthString);
        System.out.printf("partition arr[%d, %d] and arr[%d, %d]", l, p, p + 1, r);

        sort(arr, l, p, depth + 1);
        sort(arr, p + 1, r, depth + 1);

        System.out.print(depthString);
        System.out.printf("after QuickSort arr[%d, %d] :", l, r);
        for(E e: arr)
            System.out.print(e + " ");
        System.out.println();
    }

    private static <E extends Comparable<E>> int partition(E[] arr, int l, int r) {

        // randomly pick elements between arr[l, r]
        // Why r - l need to add 1? nextInt() will not include the last element bound at.
        int p = l + (new Random()).nextInt(r - l + 1);
        swap(arr, l, p);

        // set index to the pivot: x, and iterate arr[]
        /*
         * However, if choosing the first index of the whole array, which distributes two sub-arrays imbalance.
         *
         * Time complexity: O(n ^ 2) -- by operations: n + (n - 1) + (n - 2) + ... + 0 ;
         * Recursion depth: O(n) -- due to iterating the rest of array by decrementing one element each time
         * Ans: How to solve this? Select one element randomly to partition two sub-arrays more balance
         *
         * Why not choose the middle index in the ordered array to make two sub-arrays more balance? Because some test cases can design to set the smallest element in the middle to cause O(n ^ 2).
         */
        int j = l;

        for (int i = l + 1; i <= r; i++) {
            // swap(i, j) when arr[i] < arr[l]
            if (arr[i].compareTo(arr[l]) < 0) {
                j ++;
                swap(arr, i, j);
            }
        }

        // the last swap() to partition and return the index of pivot
        swap(arr, l, j);
        return j;
    }

    private static <E extends Comparable<E>> int partition2(E[] arr, int l, int r, Random rnd) {

        // randomly pick elements between arr[l, r]
        // Why r - l need to add 1? nextInt() will not include the last element bound at.
        int p = l + rnd.nextInt(r - l + 1);

        System.out.println("l: " + l + ", r: " + r + ", p: " + p);
        System.out.print("Array: ");

        swap(arr, l, p);

        // Why not choose the middle index in the ordered array to make two sub-arrays more balance? Because some test cases can design to set the smallest element in the middle to cause O(n ^ 2).
        // Test Case: generateSpecialArray();
//        swap(arr, l, l + (r - l) / 2);

        // set index to the pivot: x, and iterate arr[]
        /*
         * However, if choosing the first index of the whole array, which distributes two sub-arrays imbalance.
         *
         * Time complexity: O(n ^ 2) -- by operations: n + (n - 1) + (n - 2) + ... + 0 ;
         * Recursion depth: O(n) -- due to iterating the rest of array by decrementing one element each time
         * Ans: How to solve this? Select one element randomly to partition two sub-arrays more balance
         *
         * Why not choose the middle index in the ordered array to make two sub-arrays more balance? Because some test cases can design to set the smallest element in the middle to cause O(n ^ 2).
         */
        int j = l;

        for (int i = l + 1; i <= r; i++) {
            // swap(i, j) when arr[i] < arr[l]
            System.out.print(arr[i] + " ");
            if (arr[i].compareTo(arr[l]) < 0) {
                j ++;
                swap(arr, i, j);
            }
        }

        System.out.println();

        // the last swap() to partition and return the index of pivot
        swap(arr, l, j);
        return j;


    }

    private static String generateDepthString(int depth) {

        StringBuilder res = new StringBuilder();

        for (int i = 0; i < depth; i ++) {
            res.append("--");
        }
        return res.toString();
    }

    private static <E> void swap(E[] arr, int i, int j) {
        E temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {

        int n2 = 5;
        Integer[] arr = ArrayGenerator.generateRandomArray(n2, n2);
        Integer[] arr3 = ArrayGenerator.generateOrderedArray(n2);
        Integer[] arr2 = ArrayGenerator.generateRandomArray(n2, 1);

//        ArrayGenerator.generateSpecialArray(n2, 0);

        SortingHelper.sortTest("QuickSort3Ways", arr);
//        SortingHelper.sortTest("MergeSort", arr);
//        SortingHelper.sortTest("InsertionSort", arr);

//        SortingHelper.sortTest("QuickSort2", arr3);

//        SortingHelper.sortTest("QuickSort2", arr2);
    }
}
