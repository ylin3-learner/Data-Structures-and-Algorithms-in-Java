import java.util.Arrays;

// SQRT 分解 : 把一個含有 N 個元素的數組分成 N ^ 1/2 份
// 以求解区间最大值为例。我们只需要在 blocks 中，存储每个区间的最大值就好了。

/**
 * 將 blocks 的初始值設為 Integer.MIN_VALUE 是為了確保在初始化過程中能正確地找到每個區塊中的最大值。具體原因如下：
 *
 * 保證每個區塊的最大值能被正確更新：
 * 在初始化的過程中，我們會遍歷原始數組 nums，並更新每個區塊中的最大值。假設我們一開始將每個區塊的初始值設為 Integer.MIN_VALUE（即整數最小值），那麼當我們遍歷 nums 時，每當遇到一個數，它都會比 Integer.MIN_VALUE 大，因此能夠更新區塊的最大值。
 *
 * 確保所有數值都能被正確比較：
 * 因為 Integer.MIN_VALUE 是整數的最小值，它保證了在初始比較時，任何有效的數組元素都會大於它，從而可以正確地更新區塊的最大值。如果我們選擇了一個其他的初始值，可能會錯過某些更新，使得結果不正確。
 *
 * 統一處理方法：
 * 設置為 Integer.MIN_VALUE 可以統一處理每個區塊的最大值計算。無論區塊中的數值是什麼，都能保證通過比較後獲得正確的最大值，這樣避免了需要額外的條件檢查或初始化邏輯。
 */
public class MaxSQRT {

    private int[] data, blocks;  // space to store data
    private int N;  // 總元素個數
    private int B; // 每組元素個數
    private int Bn;  // 組數

    public MaxSQRT(int[] nums) {

        N = nums.length;
        if (N == 0) return;

        B = (int)Math.sqrt(N);
        Bn = N / B + (N % B > 0 ? 1 : 0);

        data = Arrays.copyOf(nums, N);

        blocks = new int[Bn];
        Arrays.fill(blocks, Integer.MIN_VALUE);

        for (int i = 0; i < N; i++)
            blocks[i / B] = Math.max(blocks[i / B], nums[i]);
    }

    // 區間最大值查詢
    public int MaxRange(int l, int r) {

        if (l < 0 || l >= N || r < 0 || r >= N || l > r) return 0;

        int bStart = l / B, bEnd = r /B;

        int res = Integer.MIN_VALUE;

        if (bStart == bEnd) {
            for (int i = l; i <= r; i++)
                res = Math.max(res, data[i]);
            return res;
        }

        // query in different blocks
        for (int i = l; i < (bStart + 1) * B; i++)
            res = Math.max(res, data[i]);
        for (int b = bStart + 1; b < bEnd; b++)
            res = Math.max(res, blocks[b]);
        for (int i = bEnd * B; i <= r; i++)
            res = Math.max(res, data[i]);
        return res;
    }

    // 單元素更新
    public void update(int index, int val) {

        if (index < 0 || index >= N) return;

        int b = index / B;  // 當前 index 所在的組別號碼

        data[index] = val;

        blocks[b] = Integer.MIN_VALUE;

        for (int i = b * B; i < Math.min((b + 1) * B, N); i++)
            blocks[b] = Math.max(blocks[b], data[i]);
    }
}
