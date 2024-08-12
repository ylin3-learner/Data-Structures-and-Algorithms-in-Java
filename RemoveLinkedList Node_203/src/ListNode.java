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

public class ListNode {
    int val;
    ListNode next;
    ListNode() {}
    ListNode(int val) { this.val = val; }
    ListNode(int val, ListNode next) { this.val = val; this.next = next; }

    // ListNode's constructive method
    // Use array to implement listNode, the cur ListNode as the head of LinkedList
    public ListNode(int[] arr) {

        if (arr == null || arr.length == 0)
            throw new IllegalArgumentException("arr can not be empty");

        this.val = arr[0];
        ListNode cur = this;
        // this 在這裡指的是當前對象，即構造函數所建立的 ListNode 對象。
        for (int i = 1; i < arr.length; i++) {
            cur.next = new ListNode(arr[i]);
            cur = cur.next;
            // 將新節點連接到當前節點的 next
            // 將 cur 移動到新的節點，以繼續下一次迭代。
        }
    }

    // 以當前節點為頭節點的linked list String
    @Override
    public String toString() {

        StringBuilder res = new StringBuilder();
        ListNode cur = this;
        while (cur != null) {
            res.append(cur.val).append(" -> ");
            cur = cur.next;
        }
        res.append(" NULL");
        return res.toString();
    }
 }
