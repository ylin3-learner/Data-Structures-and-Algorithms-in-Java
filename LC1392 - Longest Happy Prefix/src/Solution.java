public class Solution {

    // Time Limit Exceed: 1 <= s.length <= 105
    public String longestPrefix(String s) {


        // 1. 為何由後往前遍歷？
        /**
         * 為了找到最長的快樂前綴。假如我們從長到短進行檢查：
         *
         * 如果我們找到一個長度為 len 的快樂前綴，我們可以立即返回結果，因為這是最長的可能前綴。
         * 如果我們從短到長檢查，找到一個短的快樂前綴後，我們還需要繼續檢查更長的前綴，效率較低。
         */
        // 2. 為何起始點為 s.length() - 1，終點為 len >= 1？
        /**
         * 起始點 s.length() - 1：
         * 我們從 len = s.length() - 1 開始，這意味著首先考慮長度為 s.length() - 1 的前綴。
         * 因為前綴不能等於整個字符串，所以最大前綴長度為 s.length() - 1。
         *
         * 終點 len >= 1：
         * 我們遍歷到 len >= 1 為止，確保至少有一個字符。
         * 當 len = 1 時，我們考慮的是長度為 1 的前綴。
         */

        // 設 prefix s[0...len() - 1] 長度為 len(), 要找到最長prefix, 也就是前後遍歷的長度要相等
        // s[y...length - 1] => len() = length - 1 - y + 1
        // len() = length - y => len() + y = length => y = length - len()
        // s[0...length - 1] ~ s[length - len()...length - 1]

        // 3. 為何 equals() 參數設為這些？
        /**
         * 0 到 len - 1 表示字符串的前 len 個字符，即前綴。
         * s.length() - len 到 s.length() - 1 表示字符串的最後 len 個字符，即後綴。
         */
        for (int len = s.length() - 1; len >= 1; len--) {
            if (equal(s, 0, len - 1, s.length() - len, s.length()-1)) {
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

    /*
    Hash : LC 1147
    E.g. s[0...n] -> "1234" % M = ["1230" + "4"] % M => prehash = [prehash * B + (s.charAt(i) - least_char)] % M;
    E.g. s[n...0] -> "1234"  % M = ["1^(10^3)" + "234"] % M => posthash = s.charAt(i) * B ^ (s.length-1) + posthash;
    ==> (a + b) % M == (a % M + B % M) % M
    ==> (a * b) % M == (a % M * b % M) % M

    但是 "/", "-" 在 % M 之後將不會相等
    (a / b) % M != ((a % M) / (b % M)) % M
     */

    public static void main(String[] args) {
        Solution solution = new Solution();
        String s = "ababa";
        String s2 = "ababc";
        System.out.println(solution.longestPrefix(s));  // Output: ""
        System.out.println(solution.longestPrefix(s2));  // Output: "aba"

        // (x % M) / B != (x / B) % M => 1230 % 11 / 10 = 9 / 10 == 0 ; 1230 / 10 % 11 = 123 % 11 == 2
        int res = (1230 % 11);
        System.out.println("res:" + res + ", result: " +(res / 10));
        res = 1230 / 10;
        System.out.println("res:" + res + ", result: " +(res % 11));
    }
}
