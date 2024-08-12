/*
為甚麼 堆的 add() 和 extractMax() 時間複雜度都是 O(log^n) 而且不會退化成 O(n)?
1. 堆是一种完全二叉树, 在完全二叉树中，除了最底层可能不是完全填满外，其他层都是完全填满的
2. 堆的根节点一定是堆中最大（或最小）的元素，因此提取最大（或最小）元素的操作时间复杂度为 O(1)
由于堆的高度是 O(log^n)，所以在添加或删除元素时，需要执行的上浮或下沉操作的次数最多与堆的高度成正比，因此这些操作的时间复杂度都是 O(log^n)。
 */

public class MaxHeap<E extends Comparable<E>>{

    private Array<E> data;

    public MaxHeap(int capacity) {
        data = new Array<>(capacity);
    }

    public MaxHeap() {
        data = new Array<>();
    }

    // 將任意數組整理成堆的形狀
    /*
    1. 將 n 個 元素逐個插入到一個空堆中, Time Complexity = O(nlog^n)
    2. heapify() - O(n)
     */
    public MaxHeap(E[] arr) {

        data = new Array<>(arr);

        if (arr.length != 1) {
            for (int i = parent(arr.length - 1); i >= 0; i --) {
                siftDown(i);
            }
        }

    }


    public boolean isEmpty() {
        return data.isEmpty();
    }

    public int getSize() {
        return data.getSize();
    }

    // 返回完全BST的數組表示中, 一個索引所表示的元素 的父親節點的索引
    private int parent(int index) {
        if (index == 0)
            throw new IllegalArgumentException("Index-0 doesn't have parent.");
        return (index - 1) / 2;
    }

    // 返回完全BST的數組表示中, 一個索引所表示的元素 的左孩子節點的索引
    private int leftChild(int index) {
        return index * 2 + 1;
    }

    private int rightChild(int index) {
        return index * 2 + 2;
    }

    // 向堆中添加元素
    public void add(E e) {
        data.addLast(e);
        siftUp(data.getSize() - 1);
    }

    // 將新添加的元素不斷交換至父節點, 直至根節點
    private void siftUp(int k) {

        if (k < 0 || k > data.getSize())
            throw new RuntimeException("SiftUp failed. Index is illegal.");

        while (k > 0 && data.get(k).compareTo(data.get(parent(k))) > 0) {
            data.swap(k, parent(k));
            k = parent(k);
        }
    }

    // 找堆中最大元素
    public E findMax() {

        if (data.getSize() == 0)
            throw new IllegalArgumentException("Cannot findMax while heap is empty.");
        return data.get(0);
    }

    // 取出堆中最大元素
    public E extractMax() {

        E ret = findMax();

        data.swap(0, data.getSize() - 1);
        data.removeLast();

        // 維護 Heap parent node >= child nodes
        siftDown(0);
        return ret;
    }

    private void siftDown(int k) {

        // 防止 k 越界 -- 如何實現? 因為索引由左數到右, 只要左邊越界, 右邊就越界
        while (leftChild(k) < data.getSize()) {

            // 就算 k 沒越界, 但有可能 k 只有左節點 沒有右節點
            int j = leftChild(k);
            if (j + 1 < data.getSize() && data.get(j).compareTo(data.get(j + 1)) < 0)
                j = rightChild(k);
            // data[j] 是 leftChild 和 rightChild 中的最大值

            if (data.get(k).compareTo(data.get(j)) >= 0)
                break;

            // data.get(k).compareTo(data.get(j)) < 0
            data.swap(k, j);
            k = j;
        }
    }

    // 取出堆中最大元素, 並且替換成元素 e, 只進行一次 log^n 操作
    public E replace(E e) {

        E ret = findMax();

        data.set(e, 0);
        siftDown(0);
        return ret;
    }
}
