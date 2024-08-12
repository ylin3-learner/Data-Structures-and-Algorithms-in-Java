public class LinkedListQueue <E> implements Queue <E>{

    public class Node {

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
        public String toString() {return e.toString(); }
    }


    private Node head, tail;
    private int size;

    public LinkedListQueue() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public int getSize() {return size;}

    @Override
    public boolean isEmpty() {return size == 0;}

    @Override
    public void enqueue(E e) {

        if (tail == null) {
            tail = new Node(e);
            head = tail;
        }
        else {
            tail.next = new Node(e);
            tail = tail.next;
        }
        size ++;
    }

    @Override
    public E dequeue() {

        if (isEmpty())
            throw new IllegalArgumentException("Cannot dequeue from an empty queue.");

        Node retNode = head;
        head = head.next;
        retNode.next = null;
        if (head == null)
            tail = null;

        size --;
        return retNode.e;
    }

    public E getFront() {
        if (isEmpty())
            throw new IllegalArgumentException("Cannot get from an empty queue.");

        return head.e;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();

        res.append("front ");
        for (Node cur = head; cur != null; cur = cur.next) {
            res.append(cur).append("->");
        }
        res.append("NULL tail");

        return res.toString();
    }

    public static void main(String[] args) {

        LinkedListQueue<Integer> linkedListQueue = new LinkedListQueue<>();
        for (int i = 0; i < 10; i++) {
            linkedListQueue.enqueue(i);
            System.out.println(linkedListQueue);

            if (i % 3 == 2) {
                linkedListQueue.dequeue();
                System.out.println(linkedListQueue);
            }
        }
    }
}
