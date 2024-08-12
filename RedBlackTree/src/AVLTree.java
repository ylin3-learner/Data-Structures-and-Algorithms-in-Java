import java.util.ArrayList;

public class AVLTree <K extends Comparable<K>, V> implements Map<K, V>{

    private class Node {
        public K key;
        public V value;
        public Node left, right;
        public int height;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
            this.height = 1;
        }
    }

    private Node root;
    private int size;

    public AVLTree() {
        root = null;
        size = 0;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
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

    public boolean contains(K key) {
        return getNode(root, key) != null;
    }

    private int getHeight(Node node) {
        if (node == null)
            return 0;

        return node.height;
    }

    private int getBalancedFactor(Node node) {
        if (node == null)
            return 0;
        return getHeight(node.left) - getHeight(node.right);
    }

    // node : rootNode, x : node.left, y : x.left
    private Node rightRotate(Node node) {
        Node x = node.left;
        Node temp = x.right;

        x.right = node;
        node.left = temp;

        // update Height

        // y : the new rootNode
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
        x.height = 1 + Math.max(getHeight(x.left), getHeight(x.right));

        return x;
    }

    // node : rootNode, x : node.right; y : x.right;
    private Node leftRotate(Node node) {
        Node x = node.right;
        Node temp = x.left;

        // rotate
        x.left = node;
        node.right = temp;

        // update Height
        node.height = Math.max(getHeight(node.left), getHeight(node.right));
        x.height = Math.max(getHeight(x.left), getHeight(x.right));

        return x;
    }

    // 向二分搜索树中添加新的元素(key, value)
    public void add(K key, V value){
        root = add(root, key, value);
    }

    // 向以node为根的二分搜索树中插入元素(key, value)，递归算法
    // 返回插入新节点后二分搜索树的根
    private Node add(Node node, K key, V value){

        if(node == null){
            size ++;
            return new Node(key, value);
        }

        if(key.compareTo(node.key) < 0)
            node.left = add(node.left, key, value);
        else if(key.compareTo(node.key) > 0)
            node.right = add(node.right, key, value);
        else // key.compareTo(node.key) == 0
            node.value = value;

        // 更新 height -- 為何要加 1? 加上根節點自身為 1 的高度
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));

        // 計算平衡因子
        int balancedFactor = getBalancedFactor(node);
//        if (Math.abs(balancedFactor) > 1)
//            System.out.println("Unbalanced: " + balancedFactor);

        // 平衡維護 -- getBalancedFactor(node.left) >= 0 ? 代表向左傾斜
        // LL
        if (balancedFactor > 1 && getBalancedFactor(node.left) >= 0)
            return rightRotate(node);

        // RR
        if (balancedFactor < -1 && getBalancedFactor(node.right) <= 0)
            return leftRotate(node);

        // LR -- getBalancedFactor(node.left) < 0 -- 根節點的左子樹 其右子樹高度 大於 左子樹 (因為插入在左子節點的右子樹)
        if (balancedFactor > 1 && getBalancedFactor(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // RL
        if (balancedFactor < -1 && getBalancedFactor(node.right) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
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

    @Override
    public V get(K key) {
        Node node = getNode(root, key);

        return node == null ? null : node.value;
    }

    private Node remove(Node node, K key) {

        if (node == null)
            return null;

        Node retNode;

        if (key.compareTo(node.key) < 0) {
            node.left = remove(node.left, key);
            retNode = node;
        }
        else if (key.compareTo(node.key) > 0) {
            node.right = remove(node.right, key);
            retNode = node;
        } else { // key.compareTo(node.key) == 0

            if (node.left == null) {
                Node rightNode = node.right;
                node.right = null;
                size --;
                retNode = rightNode;
            } else if (node.right == null) {
                Node leftNode = node.left;
                node.left = null;
                size --;
                retNode = leftNode;
            } else { // node.left && node.right == null
                // Hibbard Deletion
                Node successor = minimum(node.right);
                successor.right = remove(node.right, successor.key);
                successor.left = node.left;
                node.left = node.right = null;
                retNode = successor;
            }
        }

        if (retNode == null)
            return null;

        // update height
        retNode.height = getHeight(retNode);

        // get balancedFactor
        int balancedFactor = getBalancedFactor(retNode);

        // LL
        if (balancedFactor > 1 && getBalancedFactor(node.left) >= 0)
            return rightRotate(retNode);

        // RR
        if (balancedFactor < -1 && getBalancedFactor(node.right) <= 0)
            return leftRotate(retNode);

        // LR
        if (balancedFactor > 1 && getBalancedFactor(node.left) < 0) {
            retNode.left = leftRotate(retNode.left);
            return rightRotate(retNode);
        }

        // RL
        if (balancedFactor < -1 && getBalancedFactor(node.right) > 0) {
            retNode.right = rightRotate(retNode.right);
            return leftRotate(retNode);
        }

        return retNode;
    }

    private Node minimum(Node node) {
        if (node.left == null)
            return node;
        return minimum(node.left);
    }

    public void set(K key, V newValue) {
        Node node = getNode(root, key);

        if (node == null)
            throw new RuntimeException(key + "doesn't exist!");

        node.value = newValue;
    }

    public boolean isBalanced() {
        return isBalanced(root);
    }

    private boolean isBalanced(Node node) {
        if (node == null)  // 就算是空樹, 左右子樹高度差不超過 1 -> 平衡
            return true;

        if (Math.abs(getBalancedFactor(node)) > 1)
            return false;

        return isBalanced(node.left) && isBalanced(node.right);
    }

    public boolean isBST() {
        ArrayList<K> keys = new ArrayList<>();

        inOrder(root, keys);
        for (int i = 1; i < keys.size(); i++) {
            if (keys.get(i-1).compareTo(keys.get(i)) > 0)
                return false;
        }

        return true;
    }

    private void inOrder(Node node, ArrayList<K> keys) {

        if (node == null)
            return;

        inOrder(node.left, keys);
        keys.add(node.key);
        inOrder(node.right, keys);
    }
}
