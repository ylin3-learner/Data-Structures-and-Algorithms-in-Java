import java.util.TreeMap;

public class WordDictionaryR {

    private class Node {
        boolean isWord;
        TreeMap<Character, Node> next;

        public Node(boolean isWord) {
            this.isWord = isWord;
            next = new TreeMap<>();
        }

        public Node() {
            this(false);
        }
    }

    private Node root;

    public WordDictionaryR() {
        root = new Node();
    }

    public void addWord(String word) {

        root = addWord(root, word, 0);
    }

    private Node addWord(Node node, String word, int index) {

        if (node == null)
            return new Node();

        if (index == word.length()) {
            node.isWord = true;
            return node;
        }

        char c = word.charAt(index);

        if (node.next.get(c) == null)
            node.next.put(c, new Node());

        node.next.put(c, addWord(node.next.get(c), word, index + 1));

        return node;
    }

    public boolean search(String word) {

        return search(root, word, 0);
    }

    private boolean search(Node node, String word, int index) {

        if (index == word.length())
            return node.isWord;

        if (node == null)
            return false;

        char c = word.charAt(index);

        if (c != '.') {

            if (node.next.get(c) == null)
                return false;

            return search(node.next.get(c), word, index + 1);
        }
        else {

            for (char nextChar : node.next.keySet())
                if (search(node.next.get(nextChar), word, index + 1))
                    return true;
            return false;
        }
    }
}
