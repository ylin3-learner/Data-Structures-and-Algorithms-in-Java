public class Solution {

    // nums[] comprised of three different numbers  0, 1, 2
    public void sortColors(int[] nums) {

        int R = 3;
        // 處理元素取值範圍 [0, R) 的計數排序
        int[] cnt = new int[R];

        // cnt[0] = num of 0 in nums; cnt[1] = num of 1 in nums; cnt[2] = num of 2 in nums
        for (int num: nums)
            cnt[num] ++;

        // Rearrange num in nums
        // How to set the correct index?
        // E.g. 4 elements, start from 0, range=[0,5]: [0], [1], ...[5] (the next index of the last element]
        for (int i = 0; i < cnt[0]; i++)
            nums[i] = 0;
        for (int i = cnt[0]; i < cnt[0] + cnt[1]; i++)
            nums[i] = 1;
        for (int i = cnt[0] + cnt[1]; i < cnt[0] + cnt[1] + cnt[2]; i++)
            nums[i] = 2;
    }
}
