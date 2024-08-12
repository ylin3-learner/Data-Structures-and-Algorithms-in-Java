import java.util.PriorityQueue;

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
    public ListNode mergeKLists(ListNode[] lists) {

        int k = lists.length;

        // Access the smallest node
        PriorityQueue<ListNode> pq = new PriorityQueue<>(
                (node1, node2) -> node1.val - node2.val
        );

        // Put all the head of linkedlist nodes in list inside pq
        for (int i = 0; i < k; i++)
            if (lists[i] != null)
                pq.add(lists[i]);

        // merge into a new linkedNode list
        ListNode dummyHead = new ListNode();
        ListNode pre = dummyHead;
        while (!pq.isEmpty()) {
            ListNode curNode = pq.poll();

            // add curNode into new ListNode
            pre.next = curNode;
            pre = pre.next;

            if (curNode.next != null)
                pq.offer(curNode.next);
        }

        return dummyHead.next;  // return the head node of ListNode
    }
}