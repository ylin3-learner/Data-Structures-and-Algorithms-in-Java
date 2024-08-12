import java.util.Arrays;

class Solution {
    public int minEatingSpeed(int[] piles, int h) {

        // set ranage
        int l = 1, r = Arrays.stream(piles).max().getAsInt();

        while (l < r) {

            // 下取整 : mid 會往下取值
            int mid = l + (r - l) / 2;

            if (eatingTime(piles, mid) <= h)
                r = mid;
            else
                // mid 向右移動, 一定可以縮小
                l = mid + 1;
        }

        return l;
    }

    private int eatingTime(int[] piles, int k) {

        int res = 0;
        for (int pile: piles) {
            res += pile / k + (pile % k > 0 ? 1 : 0);
        }
        return res;
    }
}