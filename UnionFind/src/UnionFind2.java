public class UnionFind2 implements UF{

    private int[] parent;

    public UnionFind2(int size) {

        parent = new int[size];

        for (int i = 0; i < size; i++)
            parent[i] = i;
    }

    @Override
    public int getSize() {
        return parent.length;
    }

    // 查找過程，查找元素 p 所對應的集合編號
    // O(h) 複雜度, h 為樹的高度
    private int find(int p) {

        if (p < 0 || p > parent.length)
            throw new IllegalArgumentException("p is out of bound.");

        while (p != parent[p])
            p = parent[p];
        return p;
    }

    @Override
    public boolean isConnected(int p, int q) {
        return find(p) == find(q);
    }

    // 合併元素 p 和 元素 q 所屬的集合
    // O(h) 複雜度, h 為樹的高度
    @Override
    public void unionElements(int p, int q) {

        int pRoot = find(p);
        int qRoot = find(q);

        if (pRoot == qRoot)
            return;

        parent[pRoot] = qRoot;
    }
}
