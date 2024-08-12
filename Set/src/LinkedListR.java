import javafx.util.Pair;

public class LinkedListR <E>{

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

        public Node() { this(null, null);}

        @Override
        public String toString(){
            return e.toString();
        }
    }

    // 此處不使用 虛擬頭節點 實現
    private int size;
    private Node head;

    public LinkedListR() {
        head = null;
        size = 0;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    // 創建一個新的節點，然後將其插入到指定索引位置。
    // 必須將新節點插入到指定索引位置的前一個節點後面，並調整前一個節點的指向。
    // 需要在 add 函數中返回新的節點
    public void add(int index, E e) {

        if (index < 0 || index > size)
            throw new RuntimeException("Add failed. Index is invalid.");

        // 將返回的新節點賦值給 head。這樣可以處理在鍊表頭部插入元素的情況，同時也能處理一般情況。
        head = add(head, index, e);
        size ++;
    }

    // 以節點 node 為鍊表的頭節點，以及要插入元素的索引 index 和要插入的元素 e 作為參數。
    private Node add(Node node, int index, E e) {

        // 如果索引為 0，則直接在當前節點 node 前插入一個新節點，並將新節點的 next 指向當前節點。
        // 返回新節點作為新的鍊表頭。
        if (index == 0)
            return new Node(e, node);

        // 如果索引不是 0，則遞歸調用 add 函數，將當前節點的下一個節點作為新的頭節點，並將索引減 1。
        // 這樣就會一直遞歸下去，直到找到要插入的位置。
        node.next = add(node.next, index - 1, e);

        // 返回當前節點，以保持鍊表的連接關係。
        return node;
    }

    public E remove(int index) {
        if (index < 0 || index > size)
            throw new RuntimeException("Remove failed. Invalid index.");

        // Pair 對象包含兩個元素，第一個元素是刪除指定索引後的新的鍊表頭節點，第二個元素是刪除的節點的值。
        Pair<Node, E> res = remove(head, index);

        // 將新的鍊表頭節點更新為 Pair 對象中的第一個元素。
        head = res.getKey();
        return res.getValue();
    }

    // 从以node为头结点的链表中，删除第index位置的元素
    private Pair<Node, E> remove(Node node, int index) {

        // 遞歸到底 的情況
        // Pair 對象包含兩個元素，第一個元素是刪除指定索引後的新的鍊表頭節點，第二個元素是刪除的節點的值。
        if (index == 0)
            return new Pair<Node, E>(node.next, node.e);

        Pair<Node, E> res = remove(node.next, index - 1);

        node.next = res.getKey();
        return new Pair<Node, E>(node, res.getValue());
    }

    public void removeElement(E e) {

        head = removeElement(head, e);
    }

    // 从以node为头结点的链表中，删除元素e，递归算法, 返回新的頭節點
    private Node removeElement(Node node, E e) {

        if (node == null)
            return null;

        node.next = removeElement(node.next, e);

        if (node.e.equals(e)) {
            size --;
            return node.next;
        }

        return node;
    }

    public boolean contains(E e) {

        // 如果元素 e 是 null，目前的程式碼可能會拋出 NullPointerException
        if (e == null)
            return false;

        return contains(head, e);
    }

    private boolean contains(Node node, E e) {

        if (node == null)
            return false;

        if (node.e.equals(e))
            return true;

        return contains(node.next, e);
    }

    public E get(int index) {

        if (index < 0 || index > size)
            throw new RuntimeException("Get failed. Invalid index.");

        return get(head, index);
    }

    // node as root node
    private E get(Node node, int index) {

        if (index == 0)
            return node.e;

        return get(node.next, index - 1);
    }

    public void set(int index, E e) {

        if (index < 0 || index > size)
            throw new RuntimeException("Set failed. Invalid index.");

        set(head, index, e);
    }

    // 它直接修改該索引位置對應節點的元素值。只需定位到該索引位置的節點，然後更改其值即可
    // set 函數不需要返回任何新的節點。
    private void set(Node node, int index, E e) {

        if(index == 0){
            node.e = e;
            return;
        }

        set(node.next, index - 1, e);
    }

    @Override
    public String toString() {

        StringBuilder res = new StringBuilder();

        for (Node cur = head; cur.next != null; cur = cur.next) {
            res.append(cur.e).append("->");
        }
        res.append(" NULL");
        return res.toString();
    }
}
