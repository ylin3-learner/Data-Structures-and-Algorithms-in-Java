public class Solution2 {

    private final long MOD = (long)(1e9 + 7);
    // pow26[i] = 26 ^ i
    private long[] pow26;


    public int longestDecomposition(String text) {

        pow26 = new long[text.length()-1];
        pow26[0] = 1;
        for (int i = 1; i < text.length(); i++)
            pow26[i] = pow26[i-1] * 26;

        return solve(text, 0, text.length() - 1);
    }

    // s[text, right]
    private int solve(String s, int left, int right) {

        if (left > right) return 0;

        // 從頭開始計算哈希值。動態計算整個字符串的哈希值。逐步累加字符串的哈希值，從而實現動態哈希計算。
        // Refer to LC 1392 :
        // prehash = s.charAt(0) - 'a' 直接將字符串 s 的首字符對應的哈希值存儲到 prehash 的第一個位置。
        // 可以在 O(1) 時間內查詢任意子字符串的哈希值，從而加快匹配速度。
        long prehash = 0, posthash = 0;
        for (int i = left, j = right; i < j; i++, j--) {
            prehash = (prehash * 26 + (s.charAt(i) - 'a')) % MOD;
            // How to get 26 ^ (right - j)? -- 1. FastPow 2. preprocess
            posthash = ((s.charAt(j) - 'a') * pow26[right-j] + posthash) % MOD;
            // s[left, i] == s[j, right] ?
            if (prehash == posthash && equal(s, left, i, j, right))
                return 2 + solve(s, i + 1, j-1);
        }

        return 1;
    }



    // s[l1, r1] == s[l2, r2] ?
    private boolean equal(String s, int l1, int r1, int l2, int r2) {

        for (int i = l1, j = l2; i <= r1 && j <= r2; i++, j++) {
            if (s.charAt(i) != s.charAt(j))
                return false;
        }

        return true;
    }
}
