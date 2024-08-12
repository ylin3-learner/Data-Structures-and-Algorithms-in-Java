import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

public class BucketSort {

    private BucketSort() {}

    // How does it work? Use MSDSort's idea to count all the possibilities into several bucket depended on the numbers of elements

    // Then, how can I decide which bucket to put inside?

    // E.g. 6, 12, 32, 29, 13
    // If we define there are 5 buckets here: B = 5
    // The range of value: 32 (max) - 6 (min) + 1 = 27
    // Each bucket's value: 27 / 5 (Bucket) = 5.4 -> integer : ceil(5.4) = 6 [each bucket's val range]
    // 每個桶能盛放: 6 個元素可能
    // 桶 0 [6, 11], 桶 1 [12, 17], 桶 2 [18, 23], ... 桶 4 [30, 35]

    // A: Use ([element] - [minimum element] / the [element]'s index in the val range of this arr) / [each bucket's val range] = bucket's id
    // e.g. 6 - 6 / 6 = 0

    // 但是這個 B 是人為決定的, 有一個參考嗎? 這個 B 可以看作是超參數 (Hyper-parameter)
    // 之後, 對每個桶遞歸下去

    public static void sort(Integer[] arr, int B) {

        if (B <= 1)
            throw new IllegalArgumentException("B must be >= 1");

        Integer[] temp = new Integer[arr.length];
        sort(arr, 0, arr.length - 1, B, temp);
    }

    // c : val range of each bucket
    // Time Complexity: c^2 * n / c = cn -> O(cn) == O(n)
    public static void sort2(Integer[] arr, int c) {

        // 每個桶有 c 個元素
        // Calculate bucket numbers -- Total: n / c 個桶
        int B = arr.length / c + (arr.length % c > 0 ? 1 : 0);

        // Initialize each bucket with linkedList
        LinkedList<Integer>[] buckets = new LinkedList[B];
        for (int i = 0; i < B; i++)
            buckets[i] = new LinkedList<>();

        // Find out the val range of whole elements to decide which element in which bucket
        int maxv = Integer.MIN_VALUE, minv = Integer.MAX_VALUE;
        for (int e : arr) {
            maxv = Math.max(e, maxv);
            minv = Math.min(e, minv);
        }

        // Put elements in each bucket
        for (int e: arr)
            buckets[(e - minv) / c].add(e);

        // Sort elements in each bucket list
        // Each sort's time complexity: O(c^2) - bubble Sort
        for (int i = 0; i < B; i++)
            Collections.sort(buckets[i]);


        int index = 0;
        // Put sorted elements in buckets back to arr
        // 1. How to get elements in each bucket? Iterate each elements in buckets, then each buckets[i]
        // 2. How to add back to arr? Set index of arr, then put back elements iteratively
        for (int i = 0; i < B; i++)
            for (int e: buckets[i])
                 arr[index ++]= e;
    }

    private static void sort(Integer[] arr, int left, int right, int B, Integer[] temp) {

        if (left >= right) return;

        // Get the range of val
        int maxv = Integer.MIN_VALUE, minv = Integer.MAX_VALUE;
        for (int i = left; i <= right; i++) {
            maxv = Math.max(arr[i], maxv);
            minv = Math.min(arr[i], minv);
        }

        // Edge case
        if (maxv == minv) return;

        // Bucket sort

        // 1. Make bucket
        int[] cnt = new int[B];

        // 2. Get the index of elements
        int[] index = new int[B + 1];

        // 3. Get the interval of each bucket

        // Advantage: avoid float-int casting
        int interval = (maxv-minv + 1) / B + ((maxv-minv + 1) % B > 0 ? 1 : 0 );
        // int interval = (int)Math.ceil((double) ((maxv - minv + 1) / B));

        // O(n) - count the numbers in each bucket
        for (int i = left; i <= right; i++)
            cnt[(arr[i] - minv) / interval]++;

        // O(R)
        for (int i = 0; i < B; i++)
            index[i+1] = index[i] + cnt[i];

        // O(n)
        for (int i = left; i <= right; i++) {
            int p = (arr[i] - minv) / interval;
            temp[left + index[p]] = arr[i];
            index[p] ++;
        }

        // O(n)
        for (int i = left; i <= right; i++)
            arr[i] = temp[i];

        // Recursion
        sort(arr, left, left + index[0] - 1, B, temp);
        for (int i = 0; i < B - 1; i++)
            sort(arr, left + index[i], left + index[i+1] - 1, B, temp);
    }



    public static void main(String[] args) {

        int n = 1000000;

        Integer[] arr = ArrayGenerator.generateRandomArray(n, n);
        Integer[] arr2 = Arrays.copyOf(arr, arr.length);
        SortingHelper.sortTest("BucketSort", arr);
        SortingHelper.sortTest("BucketSort2", arr2);
    }
}
