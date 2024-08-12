// Quick Find: Version 1
public class UnionFind1 implements UF{

    private int[] id;

    public UnionFind1(int size) {

        id = new int[size];
        for (int i = 0; i < id.length; i++)
            id[i] = i;
    }

    @Override
    public int getSize() {
        return id.length;
    }

    // 查找元素 p 對應的集合編號 : Time Complexity - O(1)
    public int find(int p) {
        if (p < 0 || p > id.length)
            throw new IllegalArgumentException("p is out of bound.");
        return id[p];
    }

    @Override
    public boolean isConnected(int p, int q) {
        return find(p) == find(q);
    }

    // 合併元素 p 及 元素 q 所屬的集合 -> O(n)
    @Override
    public void unionElements(int p, int q) {

        int pID = find(p);
        int qID = find(q);

        for (int i = 0; i < id.length; i++) {

            if (id[i] == pID)
                pID = qID;
        }
    }
}
