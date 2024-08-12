import java.util.Arrays;

public class MergeSort {

    private MergeSort() {}

    public static<E extends Comparable<E>> void sort(E[] data) {
        E[] temp = Arrays.copyOf(data, data.length);
        sort(data, 0, data.length - 1, temp);
    }

    private static<E extends Comparable<E>> void sort(E[] data, int l, int r, E[] temp) {

        if (l >= r) return;

        int mid = l + (r - l) / 2;

        sort(data, l, mid, temp);
        sort(data, mid + 1, r, temp);

        if (data[mid].compareTo(data[mid+1]) > 0)
            merge(data, l, mid, r, temp);
    }

    private static<E extends Comparable<E>> void merge(E[] data, int l, int mid, int r, E[] temp) {

        // Use another space to copy the whole length of arr to sort its order by dividing into two sub-arrays
        /*

        public static void arraycopy(
                @NotNull  Object src,
                int srcPos,
                @NotNull  Object dest,
                int destPos, [Object dest starting position]
                int length )
         */
        System.arraycopy(data, l, temp, l, r - l + 1);

        // Suppose the two sub-arrays are in default order (small-large)
        // Merge Process : Compare two sub-arrays and the lesser one store into the right position of original list
        int i = l, j = mid + 1;

        // merge data[l, r]
        // loop invariant : arr[l, mid], arr[mid+1, r]
        for (int k = l; k <= r; k++) {

            // check i or j is out of bound
            if (i > mid) {  // the left array : data[l, mid] has been already stored back in data
                data[k] = temp[j]; j++;
            } else if (j > r) { // // the left array : data[mid+1, r] has been already stored back in data
                data[k] = temp[i]; i++;
            } else if (temp[i].compareTo(temp[j]) < 0) {
                data[k] = temp[i]; i++;
            } else {
                data[k] = temp[j]; j++;
            }
        }
    }
}
