public class UnionFind4 implements UF{

    private int[] parent;
    private int[] rank;  // rank[i] 表示根節點為 i 的樹層數

    public UnionFind4(int size) {
        parent = new int[size];
        rank = new int[size];

        for (int i = 0; i < size; i++) {
            parent[i] = i;  // parent[i]表示第i个元素所指向的父节点
            rank[i] = 1;  // 層數為 1
        }
    }

    @Override
    public int getSize() {
        return parent.length;
    }

    private int find(int index) {
        if (index < 0 || index >= parent.length)
            throw new IllegalArgumentException("Find failed. Index is illegal.");

        while (index != parent[index]) {
            index = parent[index];
        }

        return index;
    }

    @Override
    public boolean isConnected(int p, int q) {
        return find(p) == find(q);
    }

    // 根據兩個元素所在樹的 rank 不同, 判斷合併方向
    // 將 rank 低的集合 合併 到 rank 高的集合上
    @Override
    public void unionElements(int p, int q) {

        int pRoot = parent[p];
        int qRoot = parent[q];

        if (pRoot == qRoot)
            return;

        if (rank[pRoot] < rank[qRoot])
            parent[pRoot] = qRoot;
        else if (rank[pRoot] > rank[qRoot])
            parent[qRoot] = pRoot;
        else {  // rank[pRoot] = rank[qRoot]
            parent[pRoot] = qRoot;  // OR : parent[qRoot] = pRoot
            rank[qRoot] += 1;
        }
    }
}
