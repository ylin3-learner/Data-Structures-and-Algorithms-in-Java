public class BST <K extends Comparable<K>, V> implements Map<K, V>{

    private class Node {

        public K key;
        public V value;
        public Node left, right;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }

    private Node root;
    private int size;

    public BST() {
        root = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int getSize() {
        return size;
    }

    public void add(K key, V value) {
        root = add(root, key, value);
    }

    private Node add(Node node, K key, V value) {

        if (node == null) {
            size ++;
            return new Node(key, value);
        }


        if (key.compareTo(node.key) < 0) {
            node.left = add(node.left, key, value);
        } else if (key.compareTo(node.key) > 0) {
            node.right = add(node.right, key, value);
        } else
            node.key = key;

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
            throw new IllegalArgumentException(key + "doesn't exist!");

        node.value = newValue;
    }
}
