import java.util.TreeMap;

public class WordDictionary {

    private class Node {
        public boolean isWord;
        public TreeMap<Character, Node> next;

        public Node(boolean isWord) {
            this.isWord = isWord;
            next = new TreeMap<>();
        }

        public Node() {
            this(false);
        }
    }

    private Node root;

    public WordDictionary() {
        root = new Node();
    }

    public void addWord(String word) {

        Node cur = root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);

            if (cur.next.get(c) == null)
                cur.next.put(c, new Node());
            cur = cur.next.get(c);
        }

        if (!cur.isWord) {
            cur.isWord = true;
        }
    }

    public void addWordR(String word) {

        root = addWordR(root, word, 0);
    }

    private Node addWordR(Node node, String word, int index) {

        if (node == null)
            node = new Node();

        if (index == word.length()) {
            node.isWord = true;
            return node;
        }

        char c = word.charAt(index);

        if (node.next.get(c) == null)
            node.next.put(c, new Node());

        node.next.put(c, addWordR(node.next.get(c), word, index + 1));

        return node;
    }

    public boolean search(String word) {

        return match(root, word, 0);
    }

    // 搜索 以 node 為根節點的 trie , 基於 word, 每次取出 word 中對應 index 的字符
    private boolean match(Node node, String word, int index) {

        if (index == word.length()) {
            return node.isWord;
        }

        char c = word.charAt(index);

        if (c != '.') {
            if (node.next.get(c) == null)
                return false;
            return match(node.next.get(c), word, index + 1);
        }
        else {

            for (char nextChar: node.next.keySet()) {
                if (match(node.next.get(nextChar), word, index + 1))
                    return true;
            }
            return false;
        }
    }
}

/**
 * Your WordDictionary object will be instantiated and called as such:
 * WordDictionary obj = new WordDictionary();
 * obj.addWord(word);
 * boolean param_2 = obj.search(word);
 */