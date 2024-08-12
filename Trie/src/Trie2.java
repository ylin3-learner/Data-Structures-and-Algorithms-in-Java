import java.util.HashMap;

public class Trie2 {

    private class Node {

        boolean isWord;
        HashMap<Character, Node> next;

        public Node(boolean isWord) {
            this.isWord = isWord;
            next = new HashMap<>();
        }

        public Node() {
            this(false);
        }
    }

    private Node root;
    private int size;

    public Trie2() {
        root = new Node();
    }

    public int getSize() {
        return size;
    }

    public void add(String word) {

        Node cur = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);

            if (cur.next.get(c) == null)
                cur.next.put(c, new Node());
            cur = cur.next.get(c);
        }

        if (!cur.isWord) {
            cur.isWord = true;
            size ++;
        }
    }

    public boolean contains(String word) {

        Node cur = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);

            if (cur.next.get(c) == null)
                return false;
            cur = cur.next.get(c);
        }

        return cur.isWord;
    }

    public boolean isPrefix(String prefix) {
        return isPrefix(root, prefix, 0);
    }

    private boolean isPrefix(Node node, String prefix, int index) {

        if (index == prefix.length())
            return true;

        if (node == null)
            return false;

        char c = prefix.charAt(index);

        if (node.next.get(c) == null)
            return false;

        return isPrefix(node.next.get(c), prefix, index + 1);
    }

//    public String remove(String word) {
//
//    }
}
