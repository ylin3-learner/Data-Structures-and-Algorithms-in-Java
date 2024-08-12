import java.util.Arrays;

public class MinSQRT {

    // SQRT : Split arr with N elements into N ^ 1/2 groups,
    // and each group has N ^ 1/2 elements

    private int[] data, block;
    private int B;
    private int Bn;
    private int N;

    public MinSQRT(int[] nums) {

        N = nums.length;
        if (N == 0) return;

        B = (int)Math.sqrt(N);
        Bn = N / B + (N % B > 0 ? 1: 0);

        data = Arrays.copyOf(nums, N);

        block = new int[Bn];
        Arrays.fill(block, Integer.MAX_VALUE);

        for (int i = 0; i < N; i++) {
            block[i/B] = Math.min(block[i/B], data[i]);
        }
    }

    public int MinRange(int l, int r) {

        if (l < 0 || l >= N || r < 0 || r >= N || l > r) return 0;

        int bStart = l / B, bEnd = r / B;

        int res = Integer.MAX_VALUE;
        if (bStart == bEnd) {
            for (int i = l; i <= r; i++)
                res = Math.min(res, data[i]);
            return res;
        }

        // In different group
        for (int i = l; i < (bStart + 1) * B; i++)
            res = Math.min(res, data[i]);
        for (int b = bStart + 1; b < bEnd; b++)
            res = Math.min(res, block[b]);
        for (int i = bEnd * B; i <= r; i++)
            res = Math.min(res, data[i]);
        return res;
    }

    // 單元素更新
    public void update(int index, int val) {
        if (index < 0 || index >= N) return;

        int b = index / B;

        data[index] = val;

        block[b] = Integer.MAX_VALUE;

        for (int i = b * B; i < Math.min((b+1) * B, N); i++)
            block[b] = Math.min(block[b], data[i]);
    }
}
