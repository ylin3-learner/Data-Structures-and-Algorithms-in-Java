import java.util.Random;

public class Solution {

    private int[] presum;  // start index of each w[i]
    private int sum;
    private Random rnd;

    public Solution(int[] w) {
        // 要抽取彩票, 只要知道區間就可以了 (因為彩票是連續的)
        presum = new int[w.length];
        for (int i = 1; i < w.length; i++)
            // presum[i] -> 第 i 個任務的第一張彩票 , presum[i - 1] -> 第 i - 1 個任務的第一張彩票, w[i - 1] (第 i - 1 個任務的權重)
            // 第 i - 1 個任務的範圍： presum[i - 1]...presum[i - 1] + w[i - 1] - 1
            presum[i] = presum[i - 1] + w[i - 1];
        sum = presum[w.length - 1] + w[w.length - 1];

        rnd = new Random();
    }

    public int pickIndex() {

        int x = rnd.nextInt(sum);

        // 滿足 presum[res] <= x 的最大值
        int l = 0, r = presum.length - 1;
        while (l < r) {

            int mid = (l + r + 1) / 2;  // 下取整, 防止死循環
            if (presum[mid] <= x) l = mid;
            else r = mid - 1;
        }

        return l;
    }
}

/**
 * 假設我們有 5 個任務，它們的權重分別為：
 *
 * 任務 0： W[0]=3
 * 任務 1：W[1]=1
 * 任務 2：W[2]=4
 * 任務 3：W[3]=2
 * 任務 4：W[4]=5
 * 計算累積權重
 * 我們首先計算累積權重：
 * 累積權重: if 任務 n - 1:
 * start = w[0] + w[1] + ... + w[n-2]
 * end = start + w[n-1] - 1

 * 累積權重[0] = 3
 * 累積權重[1] = 3 + 1 = 4
 * 累積權重[2] = 3 + 1 + 4 = 8
 * 累積權重[3] = 3 + 1 + 4 + 2 = 10
 * 累積權重[4] = 3 + 1 + 4 + 2 + 5 = 15
 * 確定每個任務的區間
 * 每個任務的區間為其累積權重範圍：
 *
 * 任務 0：區間為 [0, 3)
 * 任務 1：區間為 [3, 4)
 * 任務 2：區間為 [4, 8)
 * 任務 3：區間為 [8, 10)
 * 任務 4：區間為 [10, 15)
 * 換句話說，這些區間可以理解為：
 *
 * 任務 0：從 0 開始到 3 - 1 之間 (即 [0, 2]) 的數字都是屬於任務 0 的。
 * 任務 1：從 3 開始到 4 - 1 之間 (即 [3, 3]) 的數字都是屬於任務 1 的。
 * 任務 2：從 4 開始到 8 - 1 之間 (即 [4, 7]) 的數字都是屬於任務 2 的。
 * 任務 3：從 8 開始到 10 - 1 之間 (即 [8, 9]) 的數字都是屬於任務 3 的。
 * 任務 4：從 10 開始到 15 - 1 之間 (即 [10, 14]) 的數字都是屬於任務 4 的。
 */