public class Solution3 {

    public String longestPrefix(String s) {

        int[] lps = getLPS(s);

        // len - lps 中最後一個 char 的 border
        int len = lps[s.length() - 1];
        return s.substring(0, len);  // return 長度為 len 的 s 最長的前綴
    }

    private int[] getLPS(String t) {

        int[] lps = new int[t.length()]; // default value in lps : 0
        // lps[0] = 0 -> suffix or prefix cannot be string itself or null

        for (int i = 1; i < t.length(); i++) {

            int a = lps[i - 1];  // a : the LPS of s.charAt(i), 長度為 a 的 border => [0...a-1]
            while (a > 0 && t.charAt(i) != t.charAt(a)) {
                a = lps[a - 1];  // lps 中次長的 border (看lps的前綴, 求得此長度)
            }

            if (t.charAt(i) == t.charAt(a))
                lps[i] = a + 1;
        }

        return lps;
    }
}
