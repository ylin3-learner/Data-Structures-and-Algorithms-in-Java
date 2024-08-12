public class Solution {

    public static ListNode reverseList(ListNode head) {

        ListNode pre = null;
        ListNode cur = head;

        // 不能在這裡宣告 next = cur.next;  -- 有可能cur 為 null 導致 null pointer

        while (cur != null) {

            ListNode next = cur.next;

            // reverse cur listNode
            cur.next = pre;

            // move forward pre, cur
            pre = cur;
            cur = next;
        }

        // jump out while-loop when cur point to null
        // Meantime, pre point to the last listNode
        return pre;
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 6, 5};

        ListNode cur = new ListNode(arr);
        ListNode test = new ListNode(arr);
        System.out.println(cur);

        ListNode reversedList1 = reverseList(cur);
        System.out.println(reversedList1);

        // Use SolutionR
        ListNode reversedList2 = SolutionR.reverseList(test, 0);
        System.out.println(reversedList2);
    }
}
