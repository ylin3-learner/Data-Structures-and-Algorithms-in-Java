import java.util.TreeMap;

public class MapSum {

    private class Node {

        int value;
        TreeMap<Character, Node> next;

        public Node(int value) {
            this.value = value;
            next = new TreeMap<>();
        }

        public Node() {
            this(0);
        }
    }

    private Node root;

    public MapSum() {

        root = new Node();
    }

    public void insert(String key, int val) {

        Node cur = root;

        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);

            if (cur.next.get(c) == null)
                cur.next.put(c, new Node());
            cur = cur.next.get(c);
        }

        cur.value = val;
    }

    public void insertR(String key, int val) {

        root = insertR(root, key, val, 0);
    }

    private Node insertR(Node node, String key, int val, int index) {

        if (index == key.length()) {
            node.value = val;
            return node;
        }


        if (node == null)
            return new Node();

        char c = key.charAt(index);

        if (node.next.get(c) == null)
            node.next.put(c, new Node());

        node.next.put(c, insertR(node.next.get(c), key, val, index + 1));

        return node;
    }

    public int sum(String prefix) {

        Node cur = root;
        for (int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);

            if (cur.next.get(c) == null)
                return 0;
            cur = cur.next.get(c);
        }

        // Pass current prefix's root node & add all sub-nodes' value
        return sum(cur);
    }

    private int sum(Node node) {

        if (node.next.size() == 0)
            return node.value;

        int res = node.value;

        for (char c: node.next.keySet())
            res += sum(node.next.get(c));

        return res;
    }


}

/**
 * Your MapSum object will be instantiated and called as such:
 * MapSum obj = new MapSum();
 * obj.insert(key,val);
 * int param_2 = obj.sum(prefix);
 */