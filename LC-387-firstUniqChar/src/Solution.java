/**
 * Given a string s, find the first non-repeating character in it and return its index. If it does not exist, return -1
 * s consists of only lowercase English letters. -> only 26 alphabets
 *
 * Non-repeating : frequency of each character in the String
 * -> Hash Map OR dictionary : <Character, count of Character>
 */

public class Solution {
    public int firstUniqChar(String s) {

        int[] freq = new int[26];

        for (int i = 0; i < s.length(); i++)
            freq[s.charAt(i) - 'a'] ++;

        for (int i = 0; i < s.length(); i++) {
            if (freq[s.charAt(i) - 'a'] == 1)
                return i;
        }

        return -1;
    }
}