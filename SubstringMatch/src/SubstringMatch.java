import java.util.ArrayList;

public class SubstringMatch {

    private SubstringMatch() {}

    // Time Complexity: O(sn) -> O(n)
    // Search substring t in s, and return the first index of matched substring ; if not, return -1
    public static int bruteforce(String s, String t) {

        // How to define not found and when to return -1?
        // A: Not find t in s
        if (s.length() < t.length()) return -1;

        // Iterate s to find t
        // How to define the bound? s.length?
        // More dynamic : i + t.length() - 1 < s.length()
        // Why - 1? Index of the last char of t
        // Time Complexity: O(s)
        for (int i = 0; i + t.length() - 1 < s.length(); i++) {

            // Iterate t to get each char in t
            // Time Complexity: O(t) -> O(n)
            int j;
            for (j = 0; j < t.length(); j++) {
                if (s.charAt(i + j) != t.charAt(j)) {
                    break;
                }
            }
            // How to know get all the matched substring? j == t.length(), means out of t
            if (j == t.length()) return i;
        }

        return -1;
    }

    // Use rolling-hash technique to see t's hash value appeared in s
    /*
        Advantage: O(1) get each substring's hash value with t.length()
            - Compare hash value first, if hash value equals, then handling hash collision
        Worst Case: each substring occurs hash collision - O(mn)
            - How? String s = "aaaaaaaaaaa" (length: 10), String t = "aa" (length: 2), it will find out repeated t with [2, 10]
                RabinKarp will find out each hash value equals, then iterate t with each char ->  degenerate (退化) to O(mn)
     */
    public static int rabinKarp(String s, String t) {

        // Illegal Argument check
        if (s.length() < t.length()) return -1;
        if (t.length() == 0) return 0;

        long tHash = 0, MOD = (long)(1e9 + 7);
        long B = 256; // Why set B as 256? We didn't know which character will appear in String s, so we set 1 byte (8 bits)

        // count tHash value
        for (int i = 0; i < t.length(); i++)
            tHash = (tHash * B + t.charAt(i)) % MOD;

        // rolling-hash - add the next char's hash value, and minus the first char in t's hash value
        // The first char's value base
        long P = 1;
        for (int i = 0; i < t.length() - 1; i++)
            P = (P * B) % MOD;

        // String s's hash value with t.length() - 1
        long hash = 0;
        for (int i = 0; i < t.length() - 1; i++)
            hash = (hash * B + s.charAt(i)) % MOD;

        // iterate s with rolling hash
        for (int i = t.length() - 1; i < s.length(); i++) {

            hash = (hash * B + s.charAt(i)) % MOD;
            if (hash == tHash && equal(s, i - t.length() + 1, t))
                return i - t.length() + 1;

            // Update new s's hash value with t.length() - 1
            /*
            這段代碼是在處理字元串哈希值的更新，特別是滾動哈希（rolling hash）演算法中的一部分。具體地說，它在計算新的哈希值時使用模運算來確保哈希值不超出一個特定的範圍。
            假設你有一個時鐘，它顯示的是0到11（12小時制）。當時間超過11後，它會回到0。例如：

            12點是0點
            13點是1點
            這種行為實際上就是模運算的一種表現形式。對於12小時制的時鐘，任何時間 t 經過 t % 12 處理後，都會映射到一個0到11之間的值。
             */

            /*
            正常情况下的模运算：
            比如 hash = 14，14 % 12 = 2，表示時鐘上的2點。
            如果 hash = 25，25 % 12 = 1，表示時鐘上的1點。
            處理負值：

            有時候減去一個值後，結果會變成負數。例如 hash = 5，我們減去一個值 8：5 - 8 = -3。
            負數 -3 相當於時鐘上的一個負點，需要轉換為正點。我們可以加上 MOD（12小時），-3 + 12 = 9。
            然後我們再取模：9 % 12 = 9。
            所以，% MOD + MOD) % MOD 的作用相當於在時鐘上確保時間始終是正的並在合理的範圍內：

            如果時間是負的，我們加上一個完整的時鐘周期。
            再次取模確保時間在0到MOD-1之間。
             */
            hash = ((hash - s.charAt(i - t.length() + 1) * P) % MOD + MOD) % MOD;
        }
        return  -1;
    }

    public static int rabinKarp2(String s, String t) {
        // Illegal Argument check
        if (s.length() < t.length()) return -1;
        if (t.length() == 0) return 0;

        ArrayList<Integer> res = new ArrayList<>();

        long tHash = 0, MOD = (long)(1e9 + 7);
        long B = 256; // We use base 256 as it covers all ASCII characters (1 byte)

        // Calculate the hash value of the pattern t
        for (int i = 0; i < t.length(); i++)
            tHash = (tHash * B + t.charAt(i)) % MOD;

        // Rolling hash - add the next character's hash value, and subtract the first character's hash value in the current window
        // Calculate the base value for the first character in the window
        long P = 1;
        for (int i = 0; i < t.length() - 1; i++)
            P = (P * B) % MOD;

        // Calculate the initial hash value for the first window of s with length t.length() - 1
        long hash = 0;
        for (int i = 0; i < t.length() - 1; i++)
            hash = (hash * B + s.charAt(i)) % MOD;

        // Iterate through s with rolling hash
        for (int i = t.length() - 1; i < s.length(); i++) {
            // Add the next character's hash value to the current hash
            hash = (hash * B + s.charAt(i)) % MOD;

            // Check if the current hash matches the pattern's hash and verify the substring
            if (hash == tHash && equal(s, i - t.length() + 1, t))
                res.add(i - t.length() + 1);

            // Update the hash value for the next window
            hash = ((hash - s.charAt(i - t.length() + 1) * P) % MOD + MOD) % MOD;
        }
        return res.size();
    }


    // Iterate t with each char to compare with s[i - t.length() + 1, i]
    private static boolean equal(String s, int l, String t) {
        for (int i = 0; i < t.length() - 1; i++) {
            if (t.charAt(i) != s.charAt(i + l))
                return false;
        }

        return true;
    }

    // Time Complexity Analysis:
    /*
    while(a > 0 && t.charAt(i) != t.charAt(a))
        a = lps[a - 1];

     - Worst Case:
        Run lps[i - 1] times => everytime a -= 1 => when prefix : AAAAAAAA
        lps[i] 比 lps[i-1] 最多大 1
     */
    public static int kmp(String s, String t) {

        if (s.length() < t.length()) return -1;
        if (t.length() == 0) return 0;

        int[] lps = getLPS(t);

        int i = 0, j = 0;  // i - index of s, j - index of t
        // 為甚麼這裡使用 while, 不是 for-loop? 因為 i 索引不是每次都 ++
        while (i < s.length()) {

            if (s.charAt(i) == t.charAt(j)) {

                i ++;
                j ++;
                if (j == t.length())
                    return i - t.length();
            } else if (j > 0) {  // j > 0 表示至少要有成功匹配的前綴
                j = lps[j - 1];
            } else i ++;
        }

        return -1;
    }

    // LPS
    private static int[] getLPS(String t) {

        int[] lps = new int[t.length()]; // default lps[i] = 0
        for (int i = 1; i < t.length(); i++) {

            int a = lps[i - 1];
            // a > 0? 1. 不能索引越界小於 lps[0] 2. a = 0 表示當前沒有任何匹配成功的 border
            while (a > 0 && t.charAt(i) != t.charAt(a))
                a = lps[a - 1];

            if (t.charAt(i) == t.charAt(a))
                lps[i] = a + 1;
        }
        return lps;
    }

    public static void main(String[] args) {

        String s1 = "Hello, this is liuyubobobo";
        String t1 = "bo";

        SubstringMatchHelper.testMatch("bruteforce", s1, t1);
        SubstringMatchHelper.testMatch("rabinKarp", s1, t1);
        SubstringMatchHelper.testMatch("kmp", s1, t1);

        System.out.println();
        String s2 = FileOperation.readFile("pride-and-prejudice.txt");
        String t2 = "zyx";
        String testString = "china";
        SubstringMatchHelper.testMatch("bruteforce", s2, t2);
        SubstringMatchHelper.testMatch("rabinKarp", s2, t2);
        SubstringMatchHelper.testMatch("kmp", s2, t2);

        System.out.println();
        SubstringMatchHelper.testMatch("bruteforce", s2, testString);
        SubstringMatchHelper.testMatch("rabinKarp", s2, testString);
        SubstringMatchHelper.testMatch("kmp", s2, testString);
        // Worst case
        StringBuilder s3 = new StringBuilder();

        int n = 1000000, m = 1000;
        for (int i = 0; i < n; i++)
            s3.append("a");

        StringBuilder t3 = new StringBuilder();
        for (int i = 0; i < m - 1; i++)
            t3.append("a");
        t3.append("b");

        // O(n*m)
        System.out.println();
        SubstringMatchHelper.testMatch("bruteforce", s3.toString(), t3.toString());
        SubstringMatchHelper.testMatch("rabinKarp", s3.toString(), t3.toString());
        SubstringMatchHelper.testMatch("kmp", s3.toString(), s3.toString());

        StringBuilder worstS = new StringBuilder();
        for (int i = 0; i < n; i++)
            worstS.append("a");

        StringBuilder worstT = new StringBuilder();
        for (int i = 0; i < m; i++)
            worstT.append("a");

        System.out.println("Found numbers of worstT: " + rabinKarp2(worstS.toString(), worstT.toString()) + " ");
        System.out.println("\nWorst Case: ");
        SubstringMatchHelper.testMatch("rabinKarp", worstS.toString(), worstT.toString());
        SubstringMatchHelper.testMatch("bruteforce", worstS.toString(), worstT.toString());
        SubstringMatchHelper.testMatch("kmp", worstS.toString(), worstT.toString());

    }
}
