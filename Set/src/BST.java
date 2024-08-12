import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BST <E extends Comparable<E>>{

    private class Node {
        public E e;
        public Node left, right;

        public Node(E e) {
            this.e = e;
            left = null;
            right = null;
        }
    }

    private Node root;
    private int size;

    public BST() {
        root = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    // 向二分搜索樹插入新的元素 e
    /**
    public void add(E e) {

        // 還需要先對根節點做特殊判斷
        if (root == null) {
            root = new Node(e);
            size ++;
        }
        else
            // 此處的 add 為下方的 recursion function
            // 新的節點 加在 root 的子節點
            add(root, e);

    }


    // 向以node 為根 的二分搜索樹插入元素E - 遞規算法

    private void add(Node node, E e) {

        if (e.equals(node.e))
            return;
        else if (e.compareTo(node.e) < 0 && node.left == null) {
            node.left = new Node(e);
            size ++;
            return;
        }
        else if (e.compareTo(node.e) > 0 && node.right == null) {
            node.right = new Node(e);
            size ++;
            return;
        }

        // 此種作法的壞處：需要多考慮是否 root == null, 才進行第二次遞歸
        if (e.compareTo(node.e) < 0)
            add(node.left, e);
        else // e.compareTo(node.e) > 0
            add(node.right, e);
    }
    */

    // 改進後代碼：
    public void add(E e) {
        root = add(root, e);
    }

    // 向以node 為根節點 的二分搜索樹插入元素e - 遞歸算法
    // 直接判斷當前根節點是否為空, 對於空節點也可以看做是BST 直接先創建節點, 簡化上方冗長的判斷代碼
    // 再返回插入新節點後二分搜索樹的根
    private Node add(Node node, E e) {

        if (node == null) {
            size ++;
            // 為何需要 return new Node(e)?
            // 因為 node 只是這個函數的一個傳參變量而已, 在函數結束後, node 就會消失
            // 如果我們新創建的 new Node(e) 沒有引用指向它, 它很快就會被 GC 回收
            return new Node(e);
        }

        if (e.compareTo(node.e) < 0)
            node.left = add(node.left, e);
        else // e.compareTo(node.e) > 0
            node.right = add(node.right, e);

        return node;
    }

    // 非遞歸add 函數
    public void addNR(E e) {

        if (root == null) {
            root = new Node(e);
            size++;
            // 如果成功插入了新元素，就不需要继续遍历二分搜索树
            // 立即使用 return 语句来退出方法的执行，而不必继续执行其他代码。
            return;
        }

        // 使用一個節點 p 來追蹤當前遍歷到的節點 - 如果不為空, 繼續遍歷
        Node p = root;

        while (p != null) {

            if (e.compareTo(p.e) < 0) {
                if (p.left == null) {
                    p.left = new Node(e);
                    size ++;
                    return;
                }
                // 當 p.left 不為空, 繼續移到p.left 並以其為根節點繼續找子節點
                p = p.left;

            } else if (e.compareTo(p.e) > 0) {
                if (p.right == null) {
                    p.right = new Node(e);
                    size ++;
                    return;
                }
                p = p.right;
            } else return;
        }
    }

    public boolean contains(E e) {
        return contains(root, e);
    }

    private boolean contains(Node node, E e) {

        if (node == null)
            return false;

        if (e.compareTo(node.e) == 0)
            return true;
        else if (e.compareTo(node.e) < 0) {
            return contains(node.left, e);
        } else // e.compareTo(node.e) > 0
            return contains(node.right, e);
    }

    // contains NR
    public boolean containsNR(E e) {
        // 這句代碼確保了在樹是空的情況下，即使不進入迴圈也能夠快速返回結果，而不會浪費時間進行迴圈運算。
        if (root == null)
            return false;

        Node p = root;

        while (p != null) {
            if (e.compareTo(p.e) == 0)
                return true;

            if (e.compareTo(p.e) < 0) {
                p = p.left;
            } else if (e.compareTo(p.e) > 0) {
                p = p.right;
            }
        }

        return false;
    }

    // BST 前序遍歷, 因為遍歷為訪問所有節點, 所以兩個branches 都要遍歷
    // 前序遍歷 - 特徵：一直遍歷到最深處才返回到上一個節點 --> DFS (Depth-first Search)
    public void preOrder() {
        preOrder(root);
    }

    // 前序遍歷以 node 為 根節點 的BST, 遞歸算法
    private void preOrder(Node node) {

        if (node == null)
            return;

        // 打印輸出確認 e 相等
        // 訪問該節點
        System.out.println(node.e);

        preOrder(node.left);
        preOrder(node.right);
    }

    // BST 非遞歸前序遍歷
    public void preOrderNR() {

        // 使用棧 模擬 系統棧紀錄當前遍歷的節點位置
        Stack<Node> stack = new Stack<>();
        stack.push(root);

        while (!stack.isEmpty()) {

            Node cur = stack.pop();

            System.out.println(cur.e);

            // 為何先壓入右子樹 再 左邊?
            // 因為 Stack LIFO principle, 打印必須為先左後右
            if (cur.right != null)
                stack.push(cur.right);
            if (cur.left != null)
                stack.push(cur.left);
        }
    }

    // BST 的中序遍歷 -- 順序的結果： < x -> (root)x -> > x
    public void inOrder() {
        inOrder(root);
    }

    // 以 node 為 根節點的中序遍歷 - 遞歸算法
    private void inOrder(Node node) {

        if (node == null)
            return;

        inOrder(node.left);
        System.out.println(node.e);
        inOrder(node.right);
    }

    // BST 的後序遍歷 -- 順序的結果： < x -> > x -> (root)x
    public void postOrder() {
        postOrder(root);
    }

    // 以 node 為 根節點的後序遍歷 - 遞歸算法 => 為 BST 釋放內存
    private void postOrder(Node node) {

        if (node == null)
            return;

        postOrder(node.left);
        postOrder(node.right);
        System.out.println(node.e);
    }

    // BST 的 層序遍歷 - 廣度優先 (BFS / Breath-first Search)
    // 在無權圖中(鄰近沒有相同解), 更快找到問題的解 -> 最短路徑
    public void levelOrder() {

        Queue<Node> queue = new LinkedList<>();

        queue.add(root);

        while (!queue.isEmpty()) {

            Node cur = queue.remove();

            System.out.println(cur.e);

            // Queue : FIFO - Left -> Right
            if (cur.left != null)
                queue.add(cur.left);
            if (cur.right != null)
                queue.add(cur.right);
        }
    }

    // 尋找 BST 的最小元素 -- 一直向左走 直到 走不動, 而非向左走後一定要達到某個 leaf node
    public E minimum() {

        if (size == 0)
            throw new IllegalArgumentException("BST is empty!");

        return minimum(root).e;
    }

    // 返回以當前 node 為 根 的 BST 最小值所在的節點
    private Node minimum(Node node) {

        // halt condition
        if (node.left == null)
            return node;

        return minimum(node.left);
    }

    public E minimumNR() {

        if (size == 0)
            throw new IllegalArgumentException("BST is empty!");

        Node cur = root;
        while (cur.left != null) {
            cur = cur.left;
        }

        return cur.e;
    }

    public E maximum() {

        if (size == 0)
            throw new IllegalArgumentException("BST is empty!");

        return maximum(root).e;
    }

    private Node maximum(Node node) {

        if (node.right == null)
            return node;

        return maximum(node.right);
    }

    public E maximumNR() {

        if (size == 0)
            throw new IllegalArgumentException("BST is empty!");

        Node cur = root;

        while (cur.right != null) {
            cur = cur.right;
        }
        return cur.e;
    }

    // 從 BST 刪除最小值所在節點, 返回最小值
    public E removeMin() {

        // 為何不需檢查 size == 0 ? 因為這個檢查在 minimum 裡已經有了!
        E ret = minimum();

        // 從以 root 為 根刪除了 最小值
        // 返回 刪除最小值後對應的新 根節點, 這個 根節點 就是 新的 root
        root = removeMin(root);
        return ret;
    }

    // 刪除掉以 node 為根的 BST 最小節點, 返回刪除節點後 新的 BST 的根節點
    private Node removeMin(Node node) {

        // 遞歸到底, 但是當前以 node 為根節點還有 右子樹
        if (node.left == null) {

            Node rightNode = node.right;
            node.right = null;
            size --;
            return rightNode;
        }

        // 還沒有遞歸到底, 繼續傳入當前節點的左子樹
        // 改變 BST 結構
        node.left = removeMin(node.left);

        // 返回新的 BST 的根節點
        return node;
    }

    // 從 BST 刪除最小值所在節點, 返回最小值
    public E removeMax() {

        // 為何不需檢查 size == 0 ? 因為這個檢查在 minimum 裡已經有了!
        E ret = maximum();

        // 從以 root 為 根刪除了 最小值
        // 返回 刪除最小值後對應的新 根節點, 這個 根節點 就是 新的 root
        root = removeMax(root);
        return ret;
    }

    // 刪除掉以 node 為根的 BST 最小節點, 返回刪除節點後 新的 BST 的根節點
    private Node removeMax(Node node) {

        // 遞歸到底, 但是當前以 node 為根節點還有 右子樹
        if (node.right == null) {

            Node leftNode = node.left;
            node.left = null;
            size --;
            return leftNode;
        }

        // 還沒有遞歸到底, 繼續傳入當前節點的左子樹
        // 改變 BST 結構
        node.right = removeMax(node.right);

        // 返回新的 BST 的根節點
        return node;
    }

    // Remove(Node node) : Hibbard Deletion (1962)
    // if node.right == null -> rightNode = node.left, node.left = null, node.right = rightNode;
    // if node.left == null -> Vice Versa
    // if node.left == null && node.right == null
    // => Find s = min(node->right), Use s to replace node. Why?
    // In order to keep node.left still lesser than new root/node & current node.right still greater than new root

    // => Delete s first [removeMin()] in node.right, Use the deleted node.right to become s's new node.right (s -> right = delMin(d -> right))
    // => s.left = node.left, s is now the new root of the removed BST

    // From BST, remove the node with element E which user wanted (no need to return val)
    public void remove(E e) {
        root = remove(root, e);
    }

    private Node remove(Node node, E e) {

        if (node == null)
            return null;

        // Judge which branch in BST should keep finding
        if (e.compareTo(node.e) < 0) {
            node.left = remove(node.left, e);
            return node;
        }
        else if (e.compareTo(node.e) > 0) {
            node.right = remove(node.right, e);
            return node;
        }
        else { // e == node.e

            // if node.left == null
            if (node.left == null) {
                Node rightNode = node.right;
                node.right = null;
                size --;
                return rightNode;
            }

            // if node.right == null
            if (node.right == null) {
                Node leftNode = node.left;
                node.left = null;
                size --;
                return leftNode;
            }

            // if node.right == null && node.left == null
            // Hibbard Deletion - Successor = min(node.right)
            Node successor = minimum(node.right);
            successor.right = removeMin(node.right);

            successor.left = node.left;

            // Release the old BST branches
            node.left = node.right = null;

            return successor;
        }

        /* If using predecessor instead :
          Node predecessor = Maximum(node.left);

          node.left = removeMax(node.left);
          predecessor.right = node.right;

          node.left = node.right = null;

          return predecessor;
         */
    }

    // More Questions :
    // if node.e = 45;
    // floor() - find element lesser than e but the closest one, ceil() - find element greater than node.e but closet one
    // rank() - node.e is the Kth largest or smallest element in BST, select() - find the Kth largest or smallest element in BST
    // 維護 size BST, 維護 depth BST, 支持 duplicated element in BST

    @Override
    public String toString() {

        StringBuilder res = new StringBuilder();
        generateBSTString(root, 0, res);
        return res.toString();
    }

    // 生成以 node 為 根節點, 深度為 depth 的描述 BST 的字符串
    private void generateBSTString(Node node, int depth, StringBuilder res) {

        if (node == null) {
            res.append(generateDepthString(depth)).append(" null\n");
            return;
        }

        res.append(generateDepthString(depth)).append(node.e).append("\n");

        generateBSTString(node.left, depth + 1, res);
        generateBSTString(node.right, depth + 1, res);
    }

    private String generateDepthString(int depth) {

        StringBuilder res = new StringBuilder();
        for (int i = 0; i < depth; i++)
            res.append("--");
        return res.toString();
    }
}
