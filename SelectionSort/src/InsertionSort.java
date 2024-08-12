import java.util.Arrays;

public class InsertionSort {

    // Compared to SelectionSort, InsertionSort has O(n) in ordered data but O(n^2) in worst case.
    // SelectionSort always has O(n^2)
    private InsertionSort() {}

    public static <E extends Comparable<E>> void sort(E[] arr) {

        for (int i = 0; i < arr.length; i++) {

            // insert arr[i] into right position
//            for (int j = i; j - 1 >= 0; j--) {
//                if (arr[j].compareTo(arr[j-1]) < 0)
//                    swap(arr, j, j-1);
//                else break;
//            }

            for (int j = i; j - 1 >=0 && arr[j].compareTo(arr[j-1]) < 0; j--)
                swap(arr, j, j-1);
        }
    }

    public static <E> void swap(E[] arr, int i, int minIndex) {
        E temp = arr[i];
        arr[i] = arr[minIndex];
        arr[minIndex] = temp;
    }

    public static <E extends Comparable<E>> void sort2(E[] arr) {

        // insert arr[i] into right position
        for (int i=0; i < arr.length; i++) {
            E temp = arr[i];
            int j;
            for (j = i; j - 1 >= 0 && temp.compareTo(arr[j-1]) < 0; j--) {
                arr[j] = arr[j-1];
            }
            arr[j] = temp;
        }

    }

    public static <E extends Comparable<E>> void sort3(E[] arr) {

        for (int i = arr.length - 1; i >= 0; i--) {
            E temp = arr[i];
            // insert into proper position -- [0..i) -unordered ; [i..n) -ordered
            // index: 0, 1, 2, 3, 4, 5
            int j;
            for (j = i; j + 1 < arr.length && temp.compareTo(arr[j+1]) > 0; j++) {
                arr[j] = arr[j+1];
            }
            arr[j] = temp;
        }
    }

    public static void main(String[] args) {

        Integer[] arr = {6, 2, 4, 3, 1, 5};
        InsertionSort.sort3(arr);

        for (int e: arr) {
            System.out.print(e+" ");
        }
        System.out.println();

        System.out.println("Random Array: ");
        int[] dataSets = {10000, 100000};
        for (int n: dataSets) {
            arr = ArrayGenerator.generateRandomArray(n, n);
            Integer[] arr2 = Arrays.copyOf(arr, arr.length);
            Integer[] arr3 = Arrays.copyOf(arr, arr.length);

            SortingHelper.sortTest("InsertionSort2", arr2);
//            SortingHelper.sortTest("InsertionSort", arr);
            SortingHelper.sortTest("SelectionSort", arr3);
            SortingHelper.sortTest("InsertionSort3", arr);
        }

        System.out.println();
        System.out.println("Ordered Array: ");
        for (int e: dataSets) {
             arr = ArrayGenerator.generateOrderedArray(e);
             Integer[] arr2 = Arrays.copyOf(arr, e);

             SortingHelper.sortTest("SelectionSort", arr);
             SortingHelper.sortTest("InsertionSort2", arr2);
        }
        System.out.println();


    }
}
