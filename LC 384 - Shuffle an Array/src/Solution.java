import java.util.Random;

class Solution {

    private int[] nums;
    private Random rnd;

    public Solution(int[] nums) {
        this.nums = nums.clone();
        rnd = new Random();
    }

    public int[] reset() {
        return nums.clone();
    }

    public int[] shuffle() {

        int[] data = nums.clone();
        for (int i = data.length - 1; i >= 0; i--) {
            int j = rnd.nextInt(i + 1);
            swap(data, i , j);
        }
        return data;
    }

    public int[] shuffle2() {

        int[] data = nums.clone();  // 複製一份原數組，確保對 nums 的修改不會影響原始數據。
        for (int i = 0; i <= data.length - 1; i++) {
            int gap = data.length - i;  // 計算當前可選擇的範圍大小
            int j = i + rnd.nextInt(gap);  // 從範圍 [i, data.length - 1] 中隨機選擇一個索引 j
            swap(data, i , j);
        }
        return data;
    }

    private void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}

/**
 * Your Solution object will be instantiated and called as such:
 * Solution obj = new Solution(nums);
 * int[] param_1 = obj.reset();
 * int[] param_2 = obj.shuffle();
 */