import java.util.Arrays;

public class NumArray3 {

    private int[] data, blocks;  // data: copy of num, blocks: subgroups of num
    private int N;  // total number
    private int B;  // number of elements in blocks
    private int Bn;  // number of blocks

    public NumArray3(int[] num) {

        N = num.length;
        if (N == 0) return;

        B = (int)Math.sqrt(N);
        Bn = (N / B) + (N % B > 0 ? 1 : 0);

        data = Arrays.copyOf(num, N);

        blocks = new int[Bn];
        for (int i = 0; i < N; i++) {
            blocks[i / B] += num[i];
        }
    }

    public int sumRange(int l, int r) {

        if (l < 0 || l >= N || r < 0 || r >= N || l > r)
            return 0;  // 空區間

        int bStart = l / B, bEnd = r / B;  // 第幾組的號碼

        int res = 0;
        if (bStart == bEnd) {
            for (int i = l; i <= r; i++)
                res += data[i];
            return res;
        }

        // 不在同一組
        for (int i = l; i < (bStart + 1) * B; i++)
            res += data[i];
        for (int b = bStart + 1; b < bEnd; b++)
            res += blocks[b];
        for (int i = bEnd * B; i <= r; i++)
            res += data[i];

        return res;
    }

    public void update(int i, int val) {

        if (i < 0 || i >= N) return;

        int b = i / B;  // 第幾組

        // 更新區間值
        blocks[b] -= data[i];
        blocks[b] += val;

        // 更新原先數組
        data[i] = val;
    }
}
