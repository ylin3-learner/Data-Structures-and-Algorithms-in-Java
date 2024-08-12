import java.util.Random;

public class QuickSort3Ways {

    private QuickSort3Ways() {}

    public static <E extends Comparable<E>> void sort(E[] arr) {
        Random rnd = new Random();
        sort(arr, 0, arr.length - 1, rnd);
    }

    private static <E extends Comparable<E>> void sort(E[] arr, int l, int r, Random rnd) {

        if (l >= r) return;

        // 為何這裡不設立 partition() ? 因為Java 天然不支持返回多個變量, 需要特別設立如 Pair<Integer, Integer> 來存儲數據
        /* 三路快速排序的 partition 过程 **/
        int p = l + rnd.nextInt(r - l + 1);

        // move the randomly picked element as pivot to the first index
        swap(arr, l, p);

        // arr[gt, r] > v ; arr[lt+1, i-1] == v ; arr[l+1, lt] < v
        // According to range, initialize lt, i, gt ; how? the initial range should be null, in other words, left index larger than right
        // 初始時, 每個區間都應為空區間 : 空區間是指左邊界大於右邊界的情況

        // Two Ways Vs. Three Ways --> one index point at pivot, if smaller than move forward
        int lt = l, i = l + 1, gt = r + 1;

        // Why don't we choose for-loop? Because for-loop has unconditionally incremented or decrement compared to while-loop
        while (i < gt) {

            // if - elif - else structure allow using i to compare to l to determine swapping to which sub-arrays
            if (arr[i].compareTo(arr[l]) < 0) {
                // arr[l+1, lt] < v
                lt ++;
                swap(arr, i, lt);
                i ++;
            } else if (arr[i].compareTo(arr[l]) > 0) {
                // arr[gt, r] > v
                gt --;
                swap(arr, i, gt);
//               不能 i ++;  Why? 因為此時位於 arr[i] 上的為原來 gt 的元素, 我們需要重新比較才知道要估入哪個區間
            } else {  // arr[i] == arr[l]
                i ++;
            }
        }
        // arr[lt, gt - 1] == v
        swap(arr, l, lt);
        // 交換後 ： lt 指向 == v 的元素, 所以 arr[l, lt - 1] < v, arr[lt, gt - 1] == v, arr[gt, r] > v

        /* 三路快速排序的 partition 过程结束 **/
        // after partition : arr[l, lt - 1] < v ; arr[lt, gt - 1] == v ; arr[gt, r] > v
        // 此時在歸併, 將減少數據量跳過 == v 部分
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
        SortingHelper.sortTest("QuickSort3Ways", arr);
    }
}
