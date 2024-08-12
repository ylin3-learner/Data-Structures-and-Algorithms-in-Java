public class SolutionR {

    public static ListNode reverseList(ListNode head, int depth) {

        String depthString = generateDepthString(depth);
        System.out.print(depthString);
        System.out.println("Call: reverse" + " in " + head);

        // 例： 原: 1 -> 2 -> 3 -> 4 -> 5 ->  NULL
        if (head == null || head.next == null) {
            System.out.println("Return: " + head);
            return head;
        }


        // rev: 例: 5 -> 4 -> 3 -> 2 ->  NULL
        ListNode rev = reverseList(head.next, depth + 1);
        System.out.print(depthString);
        System.out.println("After Reverse: " + ": " + rev);

        // 原本 head.next.next 為 NULL, 現在指回 head
        head.next.next = head;
        head.next = null;

        System.out.print(depthString);
        System.out.println("Return: " + rev);

        return rev;
    }

    private static String generateDepthString(int depth) {

        StringBuilder res = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            res.append("--");
        }
        return res.toString();
    }
}
