public class LinkedList <E> {

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

        public Node() {
            this(null, null);
        }

        @Override
        public String toString() {
            return e.toString();
        }
    }

    private Node dummyHead;
    private int size;

    public LinkedList() {
        dummyHead = new Node(null, null);
        size = 0;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    // Insert new element e in the index (0-based)
    public void add(int index, E e) {

        if (index < 0 || index > size)
            throw new IllegalArgumentException("Add failed. Index is illegal.");


        Node prev = dummyHead;
        for (int i = 0; i < index; i++) {
                prev = prev.next;
        }

//      Node node = new Node(e);
//      node.next = prev.next;
//      prev.next = node;

        prev.next = new Node(e, prev.next);
        size ++;

    }

    public void addLast(E e) {
        add(size, e);
    }

    // Add new Element e at head
    public void addFirst(E e) {
    //            Node node = new Node(e);
    //            node.next = head;
    //            head = node;

        add(0, e);
    }

    // get element at index
    public E get(int index) {

        if (index < 0 || index > size)
            throw new IllegalArgumentException("Get failed. Illegal index");

        Node cur = dummyHead.next;
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }

        return cur.e;
    }

    public E getFirst() {
        return get(0);
    }

    public E getLast() {
        return get(size - 1);
    }

    // Modify element at index e
    public void set(int index, E e) {

        if (index < 0 || index > size)
            throw new IllegalArgumentException("Set failed. Illegal index.");

        Node cur = dummyHead.next;
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }

        cur.e = e;
    }

    // Check if linked list contains e
    public boolean contains(E e) {

        Node cur = dummyHead.next;
        while (cur.next != null) {

            if (cur.e.equals(e))
                return true;
            cur = cur.next;
        }

        return false;
    }

    // Delete element at index in linked list, return element
    public E remove(int index) {

        if (index < 0 || index > size)
            throw new IllegalArgumentException("Delete failed. Illegal index");

        Node prev = dummyHead;
        for (int i = 0; i < index; i++) {
            prev = prev.next;
        }

        Node delNode = prev.next;
        prev.next = delNode.next;
        delNode.next = null;
        size --;

        return delNode.e;
    }

    public E removeFirst() {
        return remove(0);
    }

    public E removeLast() {
        return remove(size - 1);
    }

    @Override
    public String toString() {

        StringBuilder res = new StringBuilder();
        res.append(String.format("LinkedList: size=%d\n", size));

//        Node cur = dummyHead.next;
//        while (cur != null) {
//            res.append(cur).append("->");
//            cur = cur.next;
//        }
        for (Node cur = dummyHead.next; cur != null; cur = cur.next) {
            res.append(cur).append("->");
        }

        res.append("NULL");
        return res.toString();
    }
}
