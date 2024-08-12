import java.util.Random;

public class Solution2 {

    private Solution2() {}

    // 在 arr[l...r] 的范围里求解整个数组的第 k 小元素并返回
    // k 是索引，即从 0 开始计算

    // 但如果題目改成求第 k 大元素, 則
    // 如果 k 為 1 表示最大值, 則在selectK 中 索引為 nums.length - 1
    // 如果 k 為 nums.length 表示最小值, 則在 selectk 中 索引為 0
    // 轉換關係: nums.length - k
    public int findKthLargest(int[] arr, int k) {

        Random rnd = new Random();
        return selectK(arr, 0, arr.length - 1, arr.length - k, rnd);
    }

    // n + n / 2 + n / 4 + n / 8 + ...  + 1 = 2n -> O(n)
    /*
     *   Java等比数列求和可以使用公式：
     *   S = a1 * (1 - q^n) / (1 - q)
     */

    public static double sumOfGeometricSequence(double a1, double q, int n) {
        return a1 * (1 - Math.pow(q, n)) / (1 - q);
    }


    public int selectK(int[] arr, int l, int r, int k, Random rnd) {

        // 假設 p 是 partition 結果 然後與 k 比較, 如果不是就在 < p 或 > p 中繼續比較
        int p = partition(arr, l, r, rnd);

        if (p == k)
            return arr[p];

        if (k < p) return selectK(arr, l, p - 1, k, rnd);
        return selectK(arr, p + 1, r, k, rnd);
    }

    // use partition() to find the Kth smallest element
    private static int partition(int[] arr, int l, int r, Random rnd) {

        // Randomly pick one element and swap with the first element to prevent arr[0] and arr[n-1]
        int p = l + rnd.nextInt(r - l + 1);

        // set randomly picked element as pivot: arr[p]
        swap(arr, l, p);

        // arr[l + 1, i - 1] < v ; arr[i, r] > v
        int i = l + 1, j = r;

        while (true) {

            while (i <= j && arr[i] < arr[l])
                i ++;

            while (j >= i && arr[j] > arr[l])
                j --;

            swap(arr, i, j);

            if (i >= j) break;

            i ++;
            j --;
        }

        swap(arr, l, j);
        return j;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        System.out.println(Solution2.sumOfGeometricSequence(10, (double) 1 /2, 5));
    }
}
