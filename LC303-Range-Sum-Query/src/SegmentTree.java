public class SegmentTree <E> {

    private E[] data;

    private E[] tree;

    private Merger<E> merger;

    public SegmentTree(E[] arr, Merger<E> merger) {

        this.merger = merger;

        data = (E[]) new Object[arr.length];
        for (int i = 0; i < arr.length; i++)
            data[i] = arr[i];

        // build tree from arr

        // allocate memory for tree
        tree = (E[]) new Object[4 * data.length];
        buildSegmentTree(0, 0, data.length - 1);
    }

    private void buildSegmentTree(int treeIndex, int l, int r) {

        if (l == r) {
            tree[treeIndex] = data[l];
            return;
        }


        int leftTreeIndex = leftChild(treeIndex);
        int rightTreeIndex = rightChild(treeIndex);

        int mid = l + (r - l) / 2;
        buildSegmentTree(leftTreeIndex, l, mid);
        buildSegmentTree(rightTreeIndex, mid + 1, r);

        tree[treeIndex] = merger.merge(tree[leftTreeIndex], tree[rightTreeIndex]);
    }

    public int leftChild(int treeIndex) {

        if (treeIndex < 0 || treeIndex >= data.length)
            throw new IllegalArgumentException("Index is illegal.");

        return treeIndex * 2 + 1;
    }

    public int rightChild(int treeIndex) {

        if (treeIndex < 0 || treeIndex >= data.length)
            throw new IllegalArgumentException("Index is illegal.");

        return treeIndex * 2 + 2;
    }

    public E get(int index) {

        if (index < 0 || index >= data.length)
            throw new IllegalArgumentException("Get failed. Index is illegal.");

        return data[index];
    }

    public E query(int queryL, int queryR) {

        if (queryL < 0 || queryL >= data.length || queryR < 0 || queryR >= data.length || queryL > queryR)
            throw new IllegalArgumentException("Query failed. Index is illegal.");

        return query(0, 0, data.length - 1, queryL, queryR);
    }

    private E query(int treeIndex, int l, int r, int queryL, int queryR) {

        if (l == queryL && r == queryR)
            return tree[treeIndex];

        int mid = l + (r - l) / 2;

        int leftTreeIndex = leftChild(treeIndex);
        int rightTreeIndex = rightChild(treeIndex);

        if (queryL >= mid + 1)
            return query(rightTreeIndex, mid + 1, r, queryL, queryR);
        else if (queryR <= mid) {
            return query(leftTreeIndex, l, mid, queryL, queryR);
        }

        E leftResult = query(leftTreeIndex, l, mid, queryL, queryR);
        E rightResult = query(rightTreeIndex, mid + 1, r, queryL, queryR);

        return merger.merge(leftResult, rightResult);
    }

    // 將 index 位置的值 更新為 e
    public void set(int index, E e) {

        if (index < 0 || index >= data.length)
            throw new IllegalArgumentException("Set failed. Index is illegal.");

        data[index] = e;

        set(0, 0, data.length - 1, index, e);
    }

    // 將 以 treeIndex 為 根節點 從[l, r] 中 在 index 的值 改成 e
    private void set(int treeIndex, int l, int r, int index, E e) {

        if (l == r) {
            tree[treeIndex] = e;
            return;
        }

        int mid = l + (r - l) / 2;
        int rightTreeIndex = leftChild(treeIndex);
        int leftTreeIndex = rightChild(treeIndex);

        if (index >= mid + 1)
            set(rightTreeIndex, mid + 1, r, index, e);
        else // index <= mid
            set(leftTreeIndex, l, mid, index, e);

        // Update the upper ancestor nodes of current index's node
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

    public static void main(String[] args) {
        Integer[] data = {-2, 0, 3, -5, 2, -1};
        SegmentTree<Integer> segmentTree = new SegmentTree<>(data, (a, b) -> a + b);
        System.out.println(segmentTree.query(0, 2));
        System.out.println(segmentTree.query(0, 5));
    }
}
