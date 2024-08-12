public class SegmentTree <E>{

    private E[] data;

    // 用數組表示樹
    private E[] tree;

    private Merger<E> merger;

    public SegmentTree(E[] arr, Merger<E> merger) {

        this.merger = merger;

        data = (E[]) new Object[arr.length];
        for (int i = 0; i < arr.length; i++)
            data[i] = arr[i];

        // Why the tree's memory is equal to 4 times of arr?
        // Because Segment tree is a balanced tree, and sometimes we can view it as a full tree.
        // Full tree have both left child and right child. Then, we can get numbers of each layers' nodes.
        // [Layer 0] : 1 , [Layer 1] : 2, [Layer 2] : 4, [Layer 3] : 8
        // We suppose this tree having [h] layers here. The total numbers of nodes are: 1 + 2 + 4 + ... + 2 ^ (h-1) = 2 ^ h - 1 ~= 2 ^ h
        // The last layer's nodes are : 2 ^ (h-1)
        // Short conclusion : Num(last layer) ~= Num(total - last layer) -- 2 ^ h - 2 ^ (h-1) = 2 ^ (h-1)

        // If we have n elements in a certain layer of tree, then we need to allocate :
        // We suppose : n = 2 ^ k; -- Require 2 * n memory for array -> Suppose last layer has n elements
        // If not enough, add one more layer -- 2 * n + 2 * n = 4 * n; -> We require (4 * n) memory for array

        tree = (E[]) new Object[4 * arr.length];
        // Turn arr into tree

        buildSegmentTree(0, 0, data.length - 1);
    }

    /**
     *
     * @param treeIndex : The root node of current tree
     * @param l : the left sub-node of the root node
     * @param r : the right sub-node of the root node
     */
    private void buildSegmentTree(int treeIndex, int l, int r) {

        // Handle the most basic case -- only has one element is each segment
        if (l == r) {
            tree[treeIndex] = data[l];  // OR : tree[treeIndex] = data[r]
            return;
        }

        int leftTreeIndex = leftChild(treeIndex);
        int rightTreeIndex = rightChild(treeIndex);

        int mid = l + (r - l) / 2;

        buildSegmentTree(leftTreeIndex, l, mid);
        buildSegmentTree(rightTreeIndex, mid + 1, r);

        // tree[treeIndex] = tree[leftTreeIndex] + tree[rightTreeIndex];
        // Operator '+' cannot be applied to E -> Create interface
        tree[treeIndex] = merger.merge(tree[leftTreeIndex], tree[rightTreeIndex]);
    }

    public E get(int index) {
        if (index < 0 || index >= data.length)
            throw new RuntimeException("Get failed. Index is illegal.");

        return data[index];
    }

    // 返回完全二叉樹的數組表示中, 一個索引所表示的元素 其左孩子節點的索引
    private int leftChild(int index) {
        return 2 * index + 1;
    }

    // 返回完全二叉樹的數組表示中, 一個索引所表示的元素 其右孩子節點的索引
    private int rightChild(int index) {
        return 2 * index + 2;
    }

    // 返回區間 [queryL, queryR] 的值
    public E query(int queryL, int queryR) {

        if (queryL < 0 || queryL >= data.length || queryR < 0 || queryR >= data.length || queryL > queryR)
            throw new IllegalArgumentException("Query failed. Index is illegal.");

        return query(0, 0, data.length - 1, queryL, queryR);
    }

    // 在以treeID 為根的線段樹中[l...r] 的範圍裡, 搜索區間[queryL, queryR]的值
    private E query(int treeIndex, int l, int r, int queryL, int queryR) {

        if (l == queryL && r == queryR)
            return tree[treeIndex];

        int mid = l + (r - l) / 2;

        int leftTreeIndex = leftChild(treeIndex);
        int rightTreeIndex = rightChild(treeIndex);

        if (queryL >= mid + 1)
            return query(rightTreeIndex, mid + 1, r, queryL, queryR);
        else if (queryR <= mid)
            return query(leftTreeIndex, l, mid, queryL, queryR);

        // queryL, queryR in different segmentTree
        // Divide [queryL, queryR] into two segment : [queryL, mid], [mid+1, queryR]
        E leftResult = query(leftTreeIndex, l, mid, queryL, queryR);
        E rightResult = query(rightTreeIndex, mid + 1, r, queryL, queryR);

        return merger.merge(leftResult, rightResult);
    }

    // 將 位置在 index 的值 更新為 e
    public void set(int index, E e) {

        if (index < 0 || index >= data.length)
            throw new IllegalArgumentException("Set failed. Index is illegal.");

        set(0, 0, data.length - 1, index, e);
    }

    // 將 以 treeIndex 為 根節點 的segmentTree 從 [l...r] 中在 index 位置的值 更新為 e
    private void set(int treeIndex, int l, int r, int index, E e) {

        if (l == r) {
            tree[treeIndex] = e;
            return;
        }

        int mid = l + (r - l) / 2;
        int leftTreeIndex = leftChild(treeIndex);
        int rightTreeIndex = rightChild(treeIndex);

        if (index >= mid + 1)
            set(rightTreeIndex, mid + 1, r, index, e);
        else // index <= mid
            set(leftTreeIndex, l, mid, index, e);

        // Update the ancestor nodes of the current index's node
        tree[treeIndex] = merger.merge(tree[leftTreeIndex], tree[rightTreeIndex]);
    }

    @Override
    public String toString() {

        StringBuilder res = new StringBuilder();
        res.append("[");

        for (int i = 0; i < tree.length; i++) {

            if (tree[i] != null)
                res.append(tree[i]);
            else
                res.append(" NULL");

            if (i != tree.length - 1)
                res.append(", ");
        }

        res.append("]");
        return res.toString();
    }
}
