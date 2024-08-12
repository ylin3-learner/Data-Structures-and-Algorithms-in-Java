import java.util.Arrays;

public class LSDSort {

    // 特點：穩定, 等長字符串, 理論複雜度：O(n), 實際複雜度：不斷訪問內存, 對於相同字符沒有提前中斷機制, 時間可能高於 O(nlog^n)
    private LSDSort(){}

    /**
     *
     * @param arr : type-String
     * @param W : length of arr
     */
    public static void sort(String[] arr, int W) {

        for (String s: arr) {
            if (s.length() != W)
                throw new IllegalArgumentException("All String's length must be the same!");
        }

        // 減少時間, 將開闢空間移到排序外面
        // 取 char 的範圍 0 ~ 256
        int R = 256;
        int[] cnt = new int[R];
        // 根據字符數量, 轉換成 index 設定各字符的起始索引
        int[] index = new int[R + 1];  // 為何是 R + 1 ? 因為總共有 R 個元素, 需要 R + 1 個空間
        // 防止覆蓋原數據, 輔助空間
        String temp[] = new String[arr.length];

        // Time Complexity: O(W * (n + R))  => W, R 常數 -> O(n)
        for (int r = W -1; r >= 0; r--) {

            // 基於 r 位字符進行計數排序

            // O(n) - 數組中元素個數
            // cnt 必須從 0 開始計數
            Arrays.fill(cnt, 0);
            for (String s: arr)
                cnt[s.charAt(r)]++;

            // O(R) 比較基準的取值範圍
            for (int i = 0; i < R; i++)  // 共有 R 個元素
                index[i+1] = index[i] + cnt[i];

            // 排序

            for (String s: arr) {
                temp[index[s.charAt(r)]] = s;
                index[s.charAt(r)]++;
            }

            for (int i = 0; i < arr.length; i++)
                arr[i] = temp[i];
        }
    }

    public static void main(String[] args) {

        String[] arr = {"BCA", "CAB", "ACB", "BAC", "ABC", "CBA"};
        LSDSort.sort(arr, 3);
        for (String s: arr)
            System.out.print(s + " ");
    }
}
