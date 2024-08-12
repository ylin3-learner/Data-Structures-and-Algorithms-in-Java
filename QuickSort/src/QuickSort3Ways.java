import java.util.Random;

public class QuickSort3Ways {

    private QuickSort3Ways() {}

    public static <E extends Comparable<E>> void sort(E[] arr) {
        Random rnd = new Random();
        sort(arr, 0, arr.length - 1, rnd);
    }

    private static <E extends Comparable<E>> void sort(E[] arr, int l, int r, Random rnd) {

        if (l >= r) return;

        /* 三路快速排序的 partition 过程 **/
        int p = l + rnd.nextInt(r - l + 1);

        // move the randomly picked element as pivot to the first index
        swap(arr, l, p);

        // arr[gt, r] > v ; arr[lt+1, i-1] == v ; arr[l+1, lt] < v
        // According to range, initialize lt, i, gt ; how? the initial range should be null, in other words, left index larger than right
        int lt = l, i = l + 1, gt = r + 1;

        // Why don't we choose for-loop? Because for-loop has unconditionally incremented or decrement compared to while-loop
        while (i < gt) {

            // if - elif - else structure allow using i to compare to l to determine swapping to which sub-arrays
            if (arr[i].compareTo(arr[l]) < 0) {
                lt ++;
                swap(arr, i, lt);
                i ++;
            } else if (arr[i].compareTo(arr[l]) > 0) {
                gt --;
                swap(arr, i, gt);
//               不能 i ++;  Why? 因為此時位於 arr[i] 上的為原來 gt 的元素, 我們需要重新比較才知道要估入哪個區間
            } else {  // arr[i] == arr[l]
                i ++;
            }
        }
        swap(arr, l, lt);

        /* 三路快速排序的 partition 过程结束 **/
        // after partition : arr[l, lt - 1] < v ; arr[lt, gt - 1] == v ; arr[gt, r] > v
        sort(arr, l, lt - 1, rnd);
        sort(arr, gt, r, rnd);
    }

    private static <E> void swap(E[] arr, int i, int j) {
        E temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        int n = 1000000;
        Integer[] arr = ArrayGenerator.generateRandomArray(n, n);
        QuickSort3Ways.sort(arr);
        SortingHelper.isSorted(arr);
    }
}
