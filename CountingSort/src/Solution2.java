public class Solution2 {

    public void sortColors(int[] nums) {

        // 處理元素取值範圍 [0, R) 的計數排序
        int R = 3;
        int[] cnt = new int[R];

        for (int num: nums)
            cnt[num] ++;

        // [index[i], index[i+1]) 的對應上方元素值為 i
        int[] index = new int[R + 1];
        for (int i = 0; i < R; i++)
            // 右邊界 = 左邊界 + cnt[i]
            index[i+1] = index[i] + cnt[i];

        // 遍歷所有 index 的區間
        for (int i = 0; i + 1 < index.length; i++)
            // [index[i], index[i+1]) 的對應上方元素值為 i
            // 從區間左邊界 遍歷到 右區間
            for (int j = index[i]; j < index[i+1]; j++)
                nums[j] = i;
    }
}
