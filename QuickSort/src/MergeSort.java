import java.util.Arrays;

public class MergeSort {

    private MergeSort() {}

    public static <E extends Comparable<E>> void sort(E[] arr) {
        sort(arr, 0, arr.length - 1);
    }

    private static <E extends Comparable<E>> void sort(E[] arr, int l, int r) {

        // basic problem
        if (l >= r) return;

        // recursion
        int mid = l + (r - l) / 2;

        sort(arr, l, mid);
        sort(arr, mid + 1, r);

        if (arr[mid].compareTo(arr[mid + 1]) >= 0)
            merge(arr, l, mid, r);
    }

    private static <E extends Comparable<E>> void merge(E[] arr, int l, int mid, int r) {

        // Create temp array to store elements of arr
        E[] temp = Arrays.copyOfRange(arr, l, r + 1);

        // arr[0, arr.length - 1] -> temp[l, r + 1]
        int i = l, j = mid + 1;

        // iterate arr and temp to merge two sub-arrays
        for (int k = l; k <= r; k++) {

            if (i > mid) {
                arr[k] = temp[j - l]; j++;
            } else if (j > r) {
                arr[k] = temp[i - l]; i++;
            } else if (temp[i - l].compareTo(temp[j - l]) <= 0) {
                arr[k] = temp[i - l]; i++;
            } else{
                arr[k] = temp[j - l]; j++;
            }
        }
    }
}
