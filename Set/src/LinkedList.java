public class LinkedList <E>{

    private class Node {
        public E e;

        public Node next;

        public Node(E e, Node next) {
            this.e = e;
            this.next = next;
        }

        public Node(E e) {
            this(e, null);
        }

        public Node() {this(null, null);}

        @Override
        public String toString() {
            return e.toString();
        }
    }

    private int size;

    private Node dummyHead;

    public LinkedList() {
        dummyHead = new Node(null, null);
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int getSize() {
        return size;
    }

    public void add(int index, E e) {

        if (index < 0 || index > size)
            throw new RuntimeException("Add failed. Index is invalid.");

        Node prev = dummyHead;

        for (int i = 0; i < size; i++) {
            prev = prev.next;
        }

        prev.next = new Node(e, prev.next);
        size ++;
    }

    public void addFirst(E e) {
        add(0, e);
    }

    // 在 remove 函數中，我們需要在鍊表中刪除特定索引處的節點。
    // 在這種情況下，我們需要找到要刪除節點的前一個節點，以便將其與後面的節點連接。
    public E remove(int index) {

        if (index < 0 || index > size)
            throw new RuntimeException("Remove failed. Index is invalid.");

        // 從 dummyHead 開始遍歷，以確保在遍歷到索引位置之前，我們能夠正確訪問索引位置前一個節點。
        Node prev = dummyHead;

        for (int i = 0; i < index; i++) {
            prev = prev.next;
        }

        Node delNode = prev.next;
        prev.next = delNode.next;
        delNode.next = null;
        // 等價於 prev.next = prev.next.next;

        size --;

        return delNode.e;
    }

    public void removeElement(E e) {

        if (e == null)
            throw new RuntimeException("Remove failed. Invalid value.");

        Node prev = dummyHead;

        // Iterate linkedList to locate duplicated e
        while (prev.next != null) {
            if (prev.next.e.equals(e)) {
                size --;
                break;
            }

            prev = prev.next;
        }

        // After locate element, delete it
        if (prev.next != null) {
            prev.next = prev.next.next;
        }
    }

    // P.S: 請注意 將 remove 函數與 contains 比較
    // 在 contains 函數中，我們是在鍊表中尋找是否包含某個元素 e
    public boolean contains(E e) {
        // 如果元素 e 是 null，目前的程式碼可能會拋出 NullPointerException
        // 可以添加一個條件來處理 e 為 null 的情況。
        if (e == null)
            return false;

        // 從頭開始遍歷鍊表時，我們實際上是從第一個真正的元素（而不是 dummyHead）開始
        Node cur = dummyHead.next; // 開始時指向第一個節點，而不是 dummyHead
        while (cur != null) {

            if (cur.e.equals(e))  // 假設了 e 不是 null
                return true;
            cur = cur.next;
        }

        return false;
    }

    public E get(int index) {

        if (index < 0 || index > size)
            throw new RuntimeException("Get failed. Index is invalid.");

        Node cur = dummyHead.next;

        for (int i = 0; i < size; i++) {
            cur = cur.next;
        }

        return cur.e;
    }

    public void set(E e, int index) {
        if (e == null)
            throw new RuntimeException("Value should be valid.");

        Node cur = dummyHead.next;

        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }

        cur.e = e;
    }

    @Override
    public String toString() {

        StringBuilder res = new StringBuilder();

        Node cur = dummyHead.next;
        while (cur != null) {
            res.append(cur.e).append("->");
            cur = cur.next;
        }

        res.append(" NULL");
        return res.toString();
    }
}
