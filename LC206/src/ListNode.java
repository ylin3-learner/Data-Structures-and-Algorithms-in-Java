public class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    ListNode(int[] arr) {

        if (arr == null || arr.length == 0)
            throw new IllegalArgumentException("arr cannot be empty.");

        // construct listNode
        ListNode cur = this;
        this.val = arr[0];

        // Remember to iterate from 1 not 0
        for (int i = 1; i < arr.length; i++) {
            cur.next = new ListNode(arr[i]);
            cur = cur.next;
        }
    }

    @Override
    public String toString() {

        StringBuilder res = new StringBuilder();

        for (ListNode cur = this; cur != null; cur = cur.next) {
            res.append(cur.val).append(" -> ");
        }

        res.append(" NULL");
        return res.toString();
    }
}