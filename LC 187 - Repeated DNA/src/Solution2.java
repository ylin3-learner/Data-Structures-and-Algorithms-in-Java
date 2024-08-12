import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Solution2 {

    public List<String> findRepeatedDnaSequences(String s) {

        // Initialize hash-char mapping
        /*
        初始化一個大小為 256 的整數數組 map：
        這是因為 ASCII 字符集的大小為 256，這樣確保我們可以映射所有可能的字符。
        即使我們只處理四種 DNA 字符（'A'、'T'、'G'、'C'），
        也需要一個足夠大的數組來處理所有字符。
         */
        int[] map = new int[256];
        map['A'] = 1;
        map['T'] = 2;
        map['G'] = 3;
        map['C'] = 4;

        // See if s is less than 10
        if (s.length() < 10) return new ArrayList<>();

        // Return once == remove duplicated - set
        // Hash - type: long
        HashSet<String> res = new HashSet<>();
        HashSet<Long> seen = new HashSet<>();

        // 1. Targeted Dna only has 10 char-length. 2. Repeated Dna only return once if match

        // Calculate hash with length of 9, excluding the 10th character (from left to right)
        long hash = 0, ten9 = (long)(1e9);
        for (int i = 0; i < 9; i++) {
            hash = hash * 10 + map[s.charAt(i)];
        }

        // Rolling hash - start from the 9th char
        for (int i = 9; i < s.length(); i++) {
            hash = hash * 10 + map[s.charAt(i)];
            if (seen.contains(hash))
                // s.substring() = [i-9, i+1)
                res.add(s.substring(i-9, i+1));
            else
                seen.add(hash);

            // 為甚麼此處不需要處理 hash collision? 因為此處根本沒有使用 模運算, 所以不會有 hash collision
            hash -= map[s.charAt(i - 9)] * ten9;
        }

        return new ArrayList<String>(res);
    }
}
