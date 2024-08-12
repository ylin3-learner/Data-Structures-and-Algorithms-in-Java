import java.util.ArrayList;

public class LinkedListSet<E> implements Set<E>{

    // 為何也可以用 LinkedList 創建 Set
    // 因為 BST 跟 LinkedList 都是動態數據結構
    // 使用 LinkedList 實現為 Unordered Set

    /*
     * BST :
     *
     * private class Node {
     *  public E e;
     *  public Node left;
     *  public Node right;
     *
     *  public Node(E e) {
     *      this.e = e;
     *      left = null;
     *      right = null;
     *  }
     * }
     *
     * LinkedList :
     *
     * private class Node {
     *  public E e;
     *  public Node next;
     *
     *  public Node(E e, Node next) {
     *      this.e = e;
     *      this.next = next;
     *  }
     *  public Node(E e) {
     *      this(e, null);
     *  }
     *  public Node() {
     *      this(null, null);
     *  }
     * }
     */

    private LinkedList<E> list;

    public LinkedListSet() {
        list = new LinkedList<>();
    }

    @Override
    public int getSize() {
        return list.getSize();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public void add(E e) {
        // Here is different from list :
        // Set couldn't have duplicated element

        if (!list.contains(e))
            list.addFirst(e);
    }

    @Override
    public void remove(E e) {
        list.removeElement(e);
    }

    @Override
    public boolean contains(E e) {
        return list.contains(e);
    }

    public static void main(String[] args) {

        System.out.println("a-tale-of-two-cities: ");
        ArrayList<String> word1 = new ArrayList<>();

        if(FileOperation.readFile("a-tale-of-two-cities.txt", word1)){
            System.out.println("Total words: " + word1.size());

            LinkedListSet<String> set1 = new LinkedListSet<>();
            for(String word: word1)
                set1.add(word);
            System.out.println("Total different words: " + set1.getSize());
        }

        System.out.println("pride-and-prejudice: ");
        ArrayList<String> word2 = new ArrayList<>();

        if(FileOperation.readFile("pride-and-prejudice.txt", word2)){
            System.out.println("Total words: " + word2.size());

            LinkedListSet<String> set2 = new LinkedListSet<>();
            for(String word: word1)
                set2.add(word);
            System.out.println("Total different words: " + set2.getSize());
        }
    }
}
