public class Solution2 {


    // Prevent Long integer overflow
    long MOD = (long)(1e9 + 7);
    public String longestPrefix(String s) {

        // pow26[i]: (26 ^ i) % MOD
        long[] pow26 = new long[s.length()];
        pow26[0] = 1;  // 26 ^ 0 = 1
        for (int i = 1; i < s.length(); i++)
            pow26[i] = (pow26[i-1] * 26) % MOD;

        // prehash: hash(s[0...s.length()-1])
        long[] prehash = new long[s.length()];
        prehash[0] = s.charAt(0) - 'a';
        for (int i = 1; i < s.length(); i++)
            prehash[i] = (prehash[i - 1] * 26 + (s.charAt(i) - 'a')) % MOD;

        // posthash: hash(s[i...s.length()-1]) -> len = s.length() - 1 - i + 1 = s.length() - i
        long[] posthash = new long[s.length()];
        posthash[s.length() - 1] = s.charAt(s.length()-1) - 'a';
        for (int i = s.length() - 2; i >= 0; i--)  // s.length() - i - 1(the first character's index)
            posthash[i] = ((s.charAt(i) - 'a') * pow26[s.length() - i - 1] + posthash[i+1]) % MOD;

        // longest - backward iteration, cannot equal to String itself, at least one character
        for (int len = s.length() - 1; len >= 1; len --) {

            // len = s[0...s.length()-1] == s[y...s.length() - 1]
            // len = s.length() - 1 - y + 1; y = s.length() - len

            // prehash[len-1]: 從字符串的開頭到位置 len-1 的前綴哈希值。len-1 是因為我們要獲得長度為 len 的前綴的哈希值。
            /*
            當 len 為 1 時，prehash[0] 是單個字符的哈希值。
            當 len 為 2 時，prehash[1] 是從開頭到第二個字符的哈希值。
             */
            // posthash[s.length()-len]: 從位置 s.length()-len 到字符串結束的後綴哈希值。這樣我們就能獲取長度為 len 的後綴的哈希值。
            /*
            當 len 為 1 時，posthash[s.length()-1] 是單個字符的哈希值（字符串的最後一個字符）。
            當 len 為 2 時，posthash[s.length()-2] 是從倒數第二個字符到字符串末尾的哈希值。
             */
            if (prehash[len-1] == posthash[s.length()-len] && equal(s, 0, s.length() - 1, s.length() - len, s.length() - 1)) {
                return s.substring(0, len);
            }
        }

        return "";
    }

    private boolean equal(String s, int l1, int r1, int l2, int r2) {
        for (int i = l1, j = l2; i <= r1 && j <= r2; i++, j++) {
            if (s.charAt(i) != s.charAt(j))
                return false;
        }

        return true;
    }

    public static void main(String[] args) {

        String s = "ababc";
        String s2 = "ababa";
        System.out.println(new Solution2().longestPrefix(s));
        System.out.println(new Solution2().longestPrefix(s2));
    }
}
