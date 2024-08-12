import java.util.ArrayList;

public class RBTree <K extends Comparable<K>, V> implements Map<K, V>{

    // Easy access for user to identify black or red
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private class Node {

        public K key;
        public V value;
        public Node left, right;
        public boolean color;  // mark if node is red or black

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
            color = RED;  // every node is ready for merging into a bigger one (two keys, or three keys) first before other operations.
        }
    }

    private Node root;
    private int size;

    public RBTree() {
        root = null;
        size = 0;
    }

    // WHY define a isRed rather than access node.color?
    // Handle null node and leave null node.color = BLACK;
    // 判斷節點的顏色
    private boolean isRed(Node node) {
        if (node == null)
            return BLACK;
        return node.color;
    }

    //    node                             x
    //   /    \    leftRotate()         /     \
    //  T1    x    ----------->        node   T3
    //       / \                       /  \
    //      T2  T3                    T1  T2

    private Node leftRotate(Node node) {

        Node x = node.right;

        // leftRotate
        node.right = x.left;
        x.left = node;

        // color
        x.color = node.color;
        node.color = RED;

        return x;
    }

    //     node                           x
    //    /    \     rightRotate()       /  \
    //   x     T2    ------------>      y   node
    //  / \                                 /  \
    // y   T1                              T1  T2
    //

    private Node rightRotate(Node node) {

        Node x = node.left;

        // rightRotate
        node.left = x.right;
        x.right = node;

        // color
        x.color = node.color;
        node.color = RED;

        return x;
    }

    // 顏色翻轉 -- 沒有返回值, 因為根節點沒有變化
    private void flipColors(Node node) {

        node.color = RED;
        node.left.color = BLACK;
        node.right.color = BLACK;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int getSize() {
        return size;
    }

    public void add(K key, V value) {
        root = add(root, key, value);
        root.color = BLACK;  // 最終根節點為 黑色
    }

    private Node add(Node node, K key, V value) {

        if (node == null) {
            size ++;
            return new Node(key, value);  // 默認插入紅色節點
        }

        // 尋找在哪裡插入新的節點 ; node / leaf node : 新插入的節點 的父親節點
        if (key.compareTo(node.key) < 0) {
            node.left = add(node.left, key, value);
        } else if (key.compareTo(node.key) > 0) {
            node.right = add(node.right, key, value);
        } else
            node.key = key;

        // 為甚麼不是 if-elif-else 的語句? 因為互相不是互斥的關係

        // 左節點是黑色 && 右節點為紅色 - 左旋轉
        if (isRed(node.right) && !isRed(node.left))
            node = leftRotate(node);  // 此時的 node 為翻轉後的新根節點

        // 左節點是紅色 && 左節點的左節點是紅色 - 右旋轉  (左傾鍊表)
        if (isRed(node.left) && isRed(node.left.left))
            node = rightRotate(node);

        // 根節點的左右節點為紅色 - 顏色翻轉
        if (isRed(node.left) && isRed(node.right))
            flipColors(node);

        return node;
    }

    private Node getNode(Node node, K key) {
        if (node == null)
            return null;

        if (key.compareTo(node.key) < 0)
            return getNode(node.left, key);
        else if (key.compareTo(node.key) > 0) {
            return getNode(node.right, key);
        } else
            return node;
    }

    public V remove(K key) {
        Node node = getNode(root, key);

        if (node != null) {
            root = remove(root, key);
            return node.value;
        }
        return null;
    }

    private Node remove(Node node, K key) {
        if (node == null)
            return null;

        if (key.compareTo(node.key) < 0) {
            node.left = remove(node.left, key);
            return node;
        } else if (key.compareTo(node.key) > 0) {
            node.right = remove(node.right, key);
            return node;
        } else { // key.compareTo(node.key == 0)

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
            } else {
                // node.left == null && node.right == null
                // Hibbard Deletion
                Node precessor = maximum(node.left, key);
                precessor.right = remove(node.right, precessor.key);
                node.left = node.right = null;
                return precessor;
            }
        }
    }

    private Node maximum(Node node, K key) {
        if (node.right == null) return node;

        return maximum(node.right, key);
    }

    public boolean contains(K key) {
        return getNode(root, key) != null;
    }

    public V get(K key) {
        Node node = getNode(root, key);
        return node == null ? null : node.value;
    }

    public void set(K key, V newValue) {
        Node node = getNode(root, key);

        if (node == null)
            throw new IllegalArgumentException(key + " doesn't exist!");

        node.value = newValue;
    }

    public static void main(String[] args) {

        System.out.println("Pride and Prejudice: ");

        ArrayList<String> words = new ArrayList<>();
        if (FileOperation.readFile("pride-and-prejudice.txt", words)) {

            System.out.println("Total Words: " + words.size());

            RBTree<String, Integer> map = new RBTree<>();
            for (String word: words) {
                if (map.contains(word))
                    map.set(word, map.get(word) + 1);
                else
                    map.add(word, 1);
            }

            System.out.println("Total different words: " + map.getSize());
            System.out.println("Frequency of Pride: " + map.get("pride"));
            System.out.println("Frequency of Prejudice: " + map.get("prejudice"));
        }

        System.out.println();
    }
}

