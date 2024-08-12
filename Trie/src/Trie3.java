public class Trie3 {

    private class Node {

        public boolean isWord;

        public Node[] next;

        public Node(boolean isWord) {
            this.isWord = isWord;
            next = new Node[26];
        }

        public Node() {
            this(false);
        }
    }

    private Node root;
    private int size;

    public Trie3() {
        root = new Node();
    }

    public void add(String word) {

        Node cur = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);

            if (cur.next[c - 'a'] == null)
                cur.next[c - 'a'] = new Node();

            cur = cur.next[c - 'a'];
        }

        if (!cur.isWord) {
            cur.isWord = true;
            size ++;
        }
    }

    public boolean contains(String word) {
        return contains(root, word, 0);
    }

    private boolean contains(Node node, String word, int index) {

        if (index == word.length())
            return node.isWord;

        if (node == null)
            return false;

        char c = word.charAt(index);

        if (node.next[c - 'a'] == null)
            return false;

        return contains(node.next[c - 'a'], word, index + 1);
    }

    public boolean isPrefix(String prefix) {

        Node cur = root;
        for (int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);

            if (cur.next[c - 'a'] == null)
                return false;
            cur = cur.next[c - 'a'];
        }

        return true;
    }
}
