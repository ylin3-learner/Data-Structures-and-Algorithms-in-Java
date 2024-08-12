import java.util.ArrayList;

public class BSTMap<K extends Comparable<K>, V> implements Map<K, V>{

    private class Node {

        public K key;
        public V value;

        public Node left, right;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            left = null;
            right = null;
        }
    }

    private Node root;
    private int size;

    public BSTMap() {
        root = null;
        size = 0;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void add(K key, V value) {
        root = add(root, key, value);
    }

    private Node add(Node node, K key, V value) {

        // 遞歸到底
        if (node == null) {
            size ++;
            return new Node(key, value);
        }

        if (key.compareTo(node.key) < 0) {
            node.left = add(node.left, key, value);
        } else if (key.compareTo(node.key) > 0) {
            node.right = add(node.right, key, value);
        } else { // key.compareTo(node.key) == 0
            node.value = value;
        }

        return node;
    }

    // 返回以 node 為 根節點的 BST 中, key 所在的節點
    private Node getNode(Node node, K key) {

        if (node == null)
            return null;

        if (key.compareTo(node.key) == 0) {
            return node;
        } else if (key.compareTo(node.key) < 0) {
            return getNode(node.left, key);
        } else { // if key.compareTo(node.key) > 0
            return getNode(node.right, key);
        }
    }

    @Override
    public boolean contains(K key) {
        return getNode(root, key) != null;
    }

    @Override
    public V get(K key) {
        Node node = getNode(root, key);
        return node.value == null ? null : node.value;
    }

    @Override
    public void set(K key, V newValue) {
        Node node = getNode(root, key);

        if (node == null)
            throw new IllegalArgumentException(key + " doesn't exist!");

        node.value = newValue;
    }

    // 返回以 node 為根 最小值 所在的節點
    private Node minimum(Node node) {
        if (node.left == null)
            return node;
        return minimum(node.left);
    }

    // 刪除以 node 為根 最小節點
    // 返回刪除後新的 根
    private Node removeMin(Node node) {

        if (node.left == null) {
            Node rightNode = node.right;
            node.right = null;
            size --;
            return rightNode;
        }

        node.left = removeMin(node.left);
        return node;
    }

    // 從 BST 刪除 鍵為 key 的節點
    public V remove(K key) {

        Node node = getNode(root, key);
        if (node != null) {
            root = remove(root, key);
            return node.value;
        }

        return null;
    }

    // 以 node 為 根節點 的BST 鍵為 key 的節點
    // 返回刪除節點後的 新 BST 的根節點
    private Node remove(Node node, K key) {

        if (node == null)
            return null;

        if (key.compareTo(node.key) < 0) {
            node.left = remove(node.left, key);
            return node;
        } else if (key.compareTo(node.key) > 0) {
            node.right = remove(node.right, key);
            return node;
        } else { // key.compareTo(node.key) == 0

            if (node.left == null) {
                Node rightNode = node.right;
                node.right = null;
                size --;
                return rightNode;
            } else if (node.right == null) {
                Node leftNode = node.left;
                node.left = null;
                size --;
                return leftNode;
            } else { // node.left != null && node.right != null

                // Hibbard Deletion
                Node successor = minimum(node.right);
                successor.right = removeMin(node.right);
                successor.left = node.left;

                node.left = node.right = null;

                return successor;

                /* If using predecessor instead :

                    Node predecessor = maximum(node.left);
                    predecessor.left = removeMax(node.left);
                    predecessor.right = node.right;

                    predecessor.left = predecessor.right = null;

                    return predecessor;
                 */
            }
        }
    }

    public static void main(String[] args) {

        System.out.println("Pride and prejudice: ");

        ArrayList<String> words = new ArrayList<>();
        if (FileOperationTest.readFile("pride-and-prejudice.txt", words)) {

            System.out.println("Total Words: " + words.size());

            BSTMap<String , Integer> map = new BSTMap<>();
            for (String word: words) {
                if (map.contains(word)) {
                    map.set(word, map.get(word) + 1);
                } else
                    map.add(word, 1);
            }

            System.out.println("Total Different Words: " + map.getSize());
            System.out.println("Frequency of Pride: " + map.get("pride"));
            System.out.println("Frequency of Prejudice: " + map.get("prejudice"));
        }
    }
}
