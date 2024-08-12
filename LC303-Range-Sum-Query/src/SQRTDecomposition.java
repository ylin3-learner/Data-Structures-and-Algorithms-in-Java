import java.util.Arrays;

public class SQRTDecomposition <E> {

    private E[] data, blocks;
    private int N;
    private int Bn;
    private int B;
    private Merger<E> merger;

    public SQRTDecomposition(E[] arr, Merger<E> merger) {

        this.merger = merger;

        N = arr.length;
        if (N == 0) return;

        B = (int)Math.sqrt(N);
        Bn = N / B + (N % B > 0 ? 1 : 0);

        data = (E[]) new Object[N];
        for (int i = 0; i < N; i++)
            data[i] = arr[i];

        // 還記得在 MinSQRT, MaxSQRT 每一個 blocks 初始值隨著功能改變而不同
        // 但如果無法確定功能, 那該如何給 block[i] 初始值?
        // 答：這個組的第一個元素 作為初值, 之後的元素則不斷和當前初值做 merge()

        // 那如何找到當前組別第一個元素? i % B == 0

        blocks = (E[]) new Object[Bn];
        for (int i = 0; i < N; i++) {
            if (i % B == 0)
                blocks[i / B] = data[i];
            else
                blocks[i / B] = merger.merge(blocks[i / B], data[i]);
        }
    }

    // 區間查詢 [l, r]
    // res 初值為 data[l], 之後從 l + 1 開始遍歷做 merge()
    public E queryRange(int l, int r) {

        if (l < 0 || l >= N || r < 0 || r >= N || l > r) return null;

        int bStart = l / B, bEnd = r / B;

        E res = data[l];

        if (bStart == bEnd) {
            for (int i = l + 1; i <= r; i++)
                res = merger.merge(res, data[i]);
            return res;
        }

        // 分布在不同區間
        for (int i = l + 1; i < (bStart + 1) * B && i <= r; i++)
            res = merger.merge(res, data[i]);
        for (int b = bStart + 1; b < bEnd; b++)
            res = merger.merge(res, blocks[b]);
        for (int i = bEnd * B; i <= r; i++)
            res = merger.merge(res, data[i]);
        return res;
    }

    // 更新 data[index], blocks[index / B]
    public void update(int index, E val) {

        if (index < 0 || index >= N) return;

        int b = index / B;

        data[index] = val;

        // blocks[b] 初值為 b * B, 即 b 組的第一個元素
        blocks[b] = blocks[b * B];
        for (int i = b * B + 1; i < Math.min((b+1) * B, N); i++)
            blocks[b] = merger.merge(blocks[b], data[i]);
    }


    private static String min(String a, String b) {
        int res = a.compareTo(b);

        return (res < 0 ? a : b);
    }

    public static void main(String[] args) {
        int n = 10;
        Integer[] arr = new Integer[n];

        for (int i = 0; i < arr.length; i++)
            arr[i] = i;
//        for (Integer e: arr)
//            System.out.print(e + " ");
        SQRTDecomposition<Integer> sumSQRT = new SQRTDecomposition<>(arr, (a, b) -> a + b);
        System.out.println("Sum: " + sumSQRT.queryRange(0, n-1));

        Float[] arr1 = new Float[n];
        for (Integer i : arr)
            arr1[i] = (float) (arr[i]);
//        for (Float e: arr1)
//            System.out.print(e + " ");
        SQRTDecomposition<Float> maxSQRT = new SQRTDecomposition<>(arr1, (a, b) -> Math.max(a, b));
        System.out.println("Max: " + maxSQRT.queryRange(0, n-1));

        String[] arr2 = new String[n];
        for (int i = 0; i < n; i++)
            arr2[i] = String.valueOf(i);
//        for (String e: arr2)
//            System.out.print(e + " ");
        SQRTDecomposition<String> minSQRT = new SQRTDecomposition<>(arr2, (a, b) -> min(a, b));
        System.out.println("Min: " + minSQRT.queryRange(0, n-1));
    }
}


