import java.util.Arrays;

public class Solution {

    public int shipWithinDays(int[] weights, int days) {

        int l = Arrays.stream(weights).max().getAsInt();
        int r = Arrays.stream(weights).sum();

        while (l < r) {

            int mid = l + (r - l) / 2;

            if (days(weights, mid) <= days)
                r = mid;
            else
                l = mid + 1;
        }

        return l;
    }

    private int days(int[] weights, int k) {

        // cur 为当前传送带的载重；res 为最终的返回结果
        int cur = 0, res = 0;

        for (int weight: weights) {// 如果当前的重量加上当前的货物没有超过 k，
            // 把当前货物重量加在 cur 上
            if (cur + weight <= k) cur += weight;
                // 否则的话，相当于从当前的货物开始，我们需要新的一天运输
                // res ++，同时，cur 更新为当前的重量
            else {
                res++;
                cur = weight;
            }
        }

        // 最后还要做一次 res ++，因为在这里 cur 肯定不为零，还记录着最后一天需要运送的货物重量
        // 最后一天，要加到 res 中，所以 res ++
        res ++;

        return res;
    }
}
