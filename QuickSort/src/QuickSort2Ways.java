import java.util.Random;

public class QuickSort2Ways {

    // QuickSort [Random Algorithm]: Use expected Value -- O(nlogn) : log^n layers * operations (n)
    private QuickSort2Ways() {}

    public static <E extends Comparable<E>> void sort(E[] arr) {

        Random rnd = new Random();
        sort(arr, 0, arr.length - 1, rnd);
    }

    private static <E extends Comparable<E>> void sort(E[] arr, int l, int r, Random rnd) {

        if (r - l <= 7) {
            InsertionSort.sort(arr);
            return;
        }

        // basic solution
        if (l >= r) return;

        // find partition not on two ended
        int p = partition(arr, l, r, rnd);

        // divide into two sub-arrays, then sort()
        sort(arr, l, p - 1, rnd);
        sort(arr, p + 1, r, rnd);
    }

    private static <E extends Comparable<E>> int partition(E[] arr, int l, int r, Random rnd) {

        // find random index to swap to two ended to prevent imbalance length of two sub-arrays
        // r - l + 1: nextInt() not include the last index element
        int p = l + rnd.nextInt(r - l + 1);
        swap(arr, l, p);

        // set index point to l, l + 1
        // l is the pivot value
//        int j = l;

//        for (int i = l + 1; i <= r; i++) {
//
//            if (arr[i].compareTo(arr[l]) < 0) {
//                j ++;
//                swap(arr, i, j);
//            }
//        }
//        swap(arr, l, j);
//        return j;

        // loop invariant: arr[l+1, i-1] <= v ; arr[j+1, r] >= v
        int i = l + 1, j = r;

        while (true) {

            while (i <= j && arr[i].compareTo(arr[l]) < 0)
                i ++;

            while (j >= i && arr[j].compareTo(arr[l]) > 0)
                j --;

            if (i >= j) break;

            // swap(i, j) when arr[i] >= 0 and arr[j] <= 0, which two elements belong to different sub-arrays
            swap(arr, i, j);
            i ++;
            j --;
        }

        // till the last move that two sub-arrays meeting each other
        swap(arr, l, j);
        return j;
    }

    private static <E> void swap(E[] arr, int i, int j) {
        E temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {

        int n = 10;

        System.out.println("Random Array:");
        Integer[] arr = ArrayGenerator.generateRandomArray(n, n);
        SortingHelper.sortTest("QuickSort2Ways", arr);

        System.out.println("Ordered Array:");
        Integer[] arr2 = ArrayGenerator.generateOrderedArray(n);
        SortingHelper.sortTest("QuickSort2Ways", arr2);

        System.out.println("Same Value:");
        Integer[] arr3 = ArrayGenerator.generateRandomArray(n, 1);
        SortingHelper.sortTest("QuickSort3Ways", arr3);

        QuickSort2Ways.sort(arr3);
        for (int e: arr) {
            System.out.print(e + " ");
        }
    }
}
