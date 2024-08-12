public class Sum {

    public static int sum(int[] arr) {
        return sum(arr, 0);
    }

    // 計算 arr[0 ... n) 範圍裡的數字和
    private static int sum(int[] arr, int n) {
        if (n == arr.length)  // 求解最基本問題
            return 0;
        return arr[n] + sum(arr, n + 1);
        // 把原問題變為更小的問題
    }

    public static void main(String[] args) {

        int[] nums = {1, 2, 3, 6, 4, 5, 6};
        System.out.println(sum(nums));
    }
}
