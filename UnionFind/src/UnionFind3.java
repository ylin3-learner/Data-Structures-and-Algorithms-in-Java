// 我們的第三版 Union-Find - 減少樹的深度 -- 比較節點總數 (總數少 指向 總數多 --> 減少合併後的集合節點深度)

public class UnionFind3 implements UF{

    private int[] parent;
    private int[] sz;  // sz[i] 表示以 i 為根 的集合中元素個數

    public UnionFind3(int size) {
        parent = new int[size];
        sz = new int[size];

        for (int i = 0; i < size; i++) {
            parent[i] = i;
            sz[i] = 1;  // 每個元素自己獨立成一個集合, 在自己的集合中只有一個元素
        }


    }

    @Override
    public int getSize() {return parent.length;};

    @Override
    public boolean isConnected(int p, int q) { return find(p) == find(q);}

    private int find(int p) {

        if (p < 0 || p > parent.length)
            throw new IllegalArgumentException("Find failed. Index is illegal");

        while (p != parent[p])
            p = parent[p];
        return p;
    }

    @Override
    public void unionElements(int p, int q) {

        int pRoot = find(p);
        int qRoot = find(q);

        if (pRoot == qRoot)
            return;

        // Judge which tree has deeper height
        if (sz[pRoot] < sz[qRoot]) {
            parent[pRoot] = qRoot;
            sz[qRoot] += sz[pRoot];
        }
        else { // sz[pRoot] >= sz[qRoot]
            parent[qRoot] = pRoot;
            sz[pRoot] += sz[qRoot];
        }
    }
}
