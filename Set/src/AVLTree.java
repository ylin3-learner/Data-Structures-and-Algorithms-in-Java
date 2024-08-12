import java.util.ArrayList;

public class AVLTree<K extends Comparable<K>, V>{

    private class Node{
        public K key;
        public V value;
        public Node left, right;
        public int height;

        public Node(K key, V value){
            this.key = key;
            this.value = value;
            left = null;
            right = null;
            height = 1;
        }
    }

    private Node root;
    private int size;

    public AVLTree(){
        root = null;
        size = 0;
    }

    public int getSize(){
        return size;
    }

    private int getHeight(Node node) {
        if (node == null)
            return 0;
        return node.height;
    }

    // 獲得節點 node 的平衡因子
    private int getBalancedFactor(Node node) {

        if (node == null)
            return 0;
        return getHeight(node.left) - getHeight(node.right);
    }

    // 右旋轉 - 對節點 y 進行右旋轉, 返回後的新根節點 x
    //        y                                            x
    //       / \                                         /   \
    //      x  T4      rightRotate(y)                   z     y
    //     / \         ----------->                    / \   / \
    //    z  T3                                       T1 T2 T3 T4
    //   / \
    //  T1 T2

    private Node rightRotate(Node y) {
        Node x = y.left;
        Node T3 = x.right;

        // rightRotate Process
        x.right = y;
        y.left = T3;

        // Update height
        y.height = 1 + Math.max(getHeight(y.left), getHeight(y.right));
        x.height = 1 + Math.max(getHeight(x.left), getHeight(x.right));

        return x;
    }

    // 對節點y進行向左旋轉操作，返回旋轉後新的根節點x
    //    y                                x
    //  /  \                             /   \
    // T1   x      leftRotate (y)       y     z
    //     / \   - - - - - - - ->      / \   / \
    //   T2   z                       T1 T2 T3 T4
    //       / \
    //      T3 T4

    private Node leftRotate(Node y) {
        Node x = y.right;
        Node T2 = x.left;

        // leftRotate Process
        x.left = y;
        y.right = T2;

        // Update height
        y.height = 1 + Math.max(getHeight(y.left), getHeight(y.right));
        x.height = 1 + Math.max(getHeight(x.left), getHeight(x.right));

        return x;

    }

    // 判斷該二叉樹是否是一棵二分搜索樹 -- 使用 inOrder 判斷是否為順序數組
    public boolean isBST() {

        ArrayList<K> keys = new ArrayList<>();
        inOrder(root, keys);
        for (int i = 1; i < keys.size(); i++)
            if (keys.get(i-1).compareTo(keys.get(i)) > 0)
                return false;
        return true;
    }

    private void inOrder(Node node, ArrayList<K> keys) {

        if (node == null)
            return;

        inOrder(node.left, keys);
        keys.add(node.key);
        inOrder(node.right, keys);
    }

    // 判斷該二叉樹是否是一棵平橫二叉樹 -- 對於每一個節點左右子樹高度叉 不超過 1 -> 遞歸算法
    public boolean isBalanced() {
        return isBalanced(root);
    }

    // 判斷以 node 為根的二叉樹是否是一棵平衡二叉樹, 遞歸算法
    private boolean isBalanced(Node node) {

        // 就算是空樹 -> 左右子樹高度沒有超過 1 -> 為 balanced tree
        if (node == null)
            return true;

        if (Math.abs(getBalancedFactor(node)) > 1)
            return false;
        // 判斷根節點的左右孩子樹
        return isBalanced(node.left) && isBalanced(node.right);
    }

    public boolean isEmpty(){
        return size == 0;
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

    // 返回以node为根节点的二分搜索树中，key所在的节点
    private Node getNode(Node node, K key){

        if(node == null)
            return null;

        if(key.equals(node.key))
            return node;
        else if(key.compareTo(node.key) < 0)
            return getNode(node.left, key);
        else // if(key.compareTo(node.key) > 0)
            return getNode(node.right, key);
    }

    public boolean contains(K key){
        return getNode(root, key) != null;
    }

    public V get(K key){

        Node node = getNode(root, key);
        return node == null ? null : node.value;
    }

    public void set(K key, V newValue){
        Node node = getNode(root, key);
        if(node == null)
            throw new IllegalArgumentException(key + " doesn't exist!");

        node.value = newValue;
    }

    // 返回以node为根的二分搜索树的最小值所在的节点
    private Node minimum(Node node){
        if(node.left == null)
            return node;
        return minimum(node.left);
    }

    // 删除掉以node为根的二分搜索树中的最小节点
    // 返回删除节点后新的二分搜索树的根
    private Node removeMin(Node node){

        if(node.left == null){
            Node rightNode = node.right;
            node.right = null;
            size --;
            return rightNode;
        }

        node.left = removeMin(node.left);
        return node;
    }

    // 从二分搜索树中删除键为key的节点
    public V remove(K key){

        Node node = getNode(root, key);
        if(node != null){
            root = remove(root, key);
            return node.value;
        }
        return null;
    }

    private Node remove(Node node, K key){

        if( node == null )
            return null;

        Node retNode;

        if( key.compareTo(node.key) < 0 ){
            node.left = remove(node.left , key);
            retNode = node;
        }
        else if(key.compareTo(node.key) > 0 ){
            node.right = remove(node.right, key);
            retNode = node;
        }
        else{   // key.compareTo(node.key) == 0

            // 待删除节点左子树为空的情况
            if(node.left == null){
                Node rightNode = node.right;
                node.right = null;
                size --;
                retNode = rightNode;
            }

            // 待删除节点右子树为空的情况
            else if(node.right == null){
                Node leftNode = node.left;
                node.left = null;
                size --;
                retNode = leftNode;
            }

            // 待删除节点左右子树均不为空的情况

            // 找到比待删除节点大的最小节点, 即待删除节点右子树的最小节点
            // 用这个节点顶替待删除节点的位置
            else {
                Node successor = minimum(node.right);

                // removeMin(node.right) --> remove(node.right, successor.key);
                successor.right = remove(node.right, successor.key);
                successor.left = node.left;

                node.left = node.right = null;

                retNode = successor;
            }
        }

        // handle null pointer
        if (retNode == null)
            return null;

        // Update height
        retNode.height = 1 + Math.max(getHeight(retNode.left), getHeight(retNode.right));

        // get balanceFactor
        int balancedFactor = getBalancedFactor(retNode);

        // Balance tree's height in four cases: LL, RR, LR, RL

        // LL
        if (balancedFactor > 1 && getBalancedFactor(retNode.left) >= 0)
            return rightRotate(retNode);

        // RR
        if (balancedFactor < -1 && getBalancedFactor(retNode.right) <= 0)
            return leftRotate(retNode);

        // LR -- leftRotate() --> LL -- rightRotate() --> balanced tree
        if (balancedFactor > 1 && getBalancedFactor(retNode.right) < 0) {
            retNode.left = leftRotate(retNode.left);
            return rightRotate(retNode);
        }

        // RL -- rightRotate() --> RR -- leftRotate() --> balanced tree
        if (balancedFactor < -1 && getBalancedFactor(retNode.left) > 0) {
            retNode.right = rightRotate(retNode.right);
            return leftRotate(retNode);
        }


        return retNode;
    }

    public static void main(String[] args){

        System.out.println("Pride and Prejudice");

        ArrayList<String> words = new ArrayList<>();
        if(FileOperation.readFile("pride-and-prejudice.txt", words)) {
            System.out.println("Total words: " + words.size());

            AVLTree<String, Integer> map = new AVLTree<>();
            for (String word : words) {
                if (map.contains(word))
                    map.set(word, map.get(word) + 1);
                else
                    map.add(word, 1);
            }

            System.out.println("Total different words: " + map.getSize());
            System.out.println("Frequency of PRIDE: " + map.get("pride"));
            System.out.println("Frequency of PREJUDICE: " + map.get("prejudice"));

            System.out.println("is BST: " + map.isBST());
            System.out.println("is Balanced: " + map.isBalanced());

            for (String word: words) {
                map.remove(word);

                if (!map.isBST() || !map.isBalanced())
                    throw new RuntimeException("Error!");
            }
        }

        System.out.println();
    }
}
