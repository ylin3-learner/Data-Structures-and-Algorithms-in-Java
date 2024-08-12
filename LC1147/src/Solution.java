public class Solution {

    public int longestDecomposition(String text) {

        return solve(text, 0, text.length()-1);
    }

    // s[left, right]
    private int solve(String text, int left, int right) {

        if (left > right) return 0;
        // text[left, i] == text[j, right] ?
        for (int i = left, j = right; i < j ;i++, j--) {
            if (equal(text, left, i, j, right))
                return 2 + solve(text, i + 1, j-1);
        }
        return 1;
    }

    // text[l1, r1] == text[l2, r2]
    private boolean equal(String s, int l1, int r1, int l2, int r2) {

        for (int i = l1, j = l2; i <= r1 && j <= r2; i++, j++) {
            if (s.charAt(i) != s.charAt(j))
                return false;
        }

        return true;
    }
}
