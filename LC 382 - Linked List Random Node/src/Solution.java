import java.util.Random;

/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {

    private ListNode head;
    private Random rnd;

    public Solution(ListNode head) {
        this.head = head;
        rnd = new Random();
    }

    public int getRandom() {

        // According to LC, range [1, 10^4] - node's val will not be null
        int res = head.val;
        int index = 1;
        for (ListNode cur = head.next; cur != null; cur = cur.next, index ++) {
            int j = rnd.nextInt(index + 1);
            if (j == 0)
                res = cur.val;
        }

        return res;
    }
}

/**
 * Your Solution object will be instantiated and called as such:
 * Solution obj = new Solution(head);
 * int param_1 = obj.getRandom();
 */