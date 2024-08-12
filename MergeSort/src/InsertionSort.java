public class InsertionSort {

    private InsertionSort() {}

    public static <E extends Comparable<E>>void sort(E[] arr) {

        for (int i = 0; i < arr.length; i++) {
            E temp = arr[i];
            int j;
            for (j = i; j - 1 >= 0 && temp.compareTo(arr[j-1]) < 0; j--) {
                arr[j] = arr[j - 1];
            }
            arr[j] = temp;
        }
    }

    public static <E extends Comparable<E>>void sort(E[] arr, int l, int r) {

        for (int i = l; i <= r; i++) {
            E temp = arr[i];
            int j;
            for (j = i; j - 1 >= l && temp.compareTo(arr[j-1]) < 0; j--) {
                arr[j] = arr[j - 1];
            }
            arr[j] = temp;
        }
    }
}
