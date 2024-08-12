public class UnionFind5 implements UF{ // Path Compression : parent(p) = parent(parent(p))

    private int[] parent; // parent[i]表示第i个元素所指向的父节点
    private int[] rank;  // rank[i] 表示 以 i 為根的集合中樹層數

    // rank[i]表示以i为根的集合所表示的树的层数
    // 在后续的代码中, 我们并不会维护rank的语意, 也就是rank的值在路径压缩的过程中, 有可能不在是树的层数值
    // 这也是我们的rank不叫height或者depth的原因, 他只是作为比较的一个标准

    public UnionFind5(int size) {

        parent = new int[size];
        rank = new int[size];

        for (int i = 0; i < size; i++) {
            parent[i] = i;
            rank[i] = 1;
        }
    }

    @Override
    public int getSize() {
        return parent.length;
    }

    private int find(int index) {

        if (index < 0 || index > parent.length)
            throw new IllegalArgumentException("Find failed. Index is illegal.");

        while (index != parent[index]) {
            parent[index] = parent[parent[index]];
            index = parent[index];
        }


        return index;
    }

    @Override
    public boolean isConnected(int p, int q) {
        return find(p) == find(q);
    }


    @Override
    public void unionElements(int p, int q) {

        int pRoot = parent[p];
        int qRoot = parent[q];

        if (pRoot == qRoot)
            return;

        if (rank[pRoot] < rank[qRoot])
            parent[pRoot] =  qRoot;
        else if (rank[pRoot] > rank[qRoot]) {
            parent[qRoot] = pRoot;
        }
        else {
            parent[pRoot] = qRoot;  // OR: parent[qRoot] = pRoot
            rank[qRoot] += 1;
        }
    }
}
