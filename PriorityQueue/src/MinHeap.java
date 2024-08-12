import java.util.ArrayList;

public class MinHeap <E extends Comparable<E>>{

    private Array<E> data;

    private int size;

    public MinHeap() {
        data = new Array<>();
    }

    public MinHeap(int capacity) {
        data = new Array<>(capacity);
    }

    public MinHeap(E[] arr) {

        data = new Array<>(arr);

        if (arr.length <= 1) return;
        for (int i = parent(arr.length - 1); i >= 0; i--)
            siftDown(i);
    }

    // 下沉 : 關注子節點是否越界 > data.getSize, 選取當中較小值最為新的父親節點
    private void siftDown(int k) {

        while (leftChild(k) < data.getSize()) {
            int j = leftChild(k);

            if (j + 1 < data.getSize() - 1 && data.get(j).compareTo(data.get(j + 1)) > 0)
                j = rightChild(k);

            if (data.get(k).compareTo(data.get(j)) <= 0)
                break;

            data.swap(k, j);
            k = j;
        }
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int getSize() {
        return size;
    }

    // leftChild : 3, parent(3) = 1 ; rightChild : 4, parent(4) = 1
    private int parent(int k) {

        if (k < 0 || k > getSize())
            throw new IllegalArgumentException("Index-0 doesn't have parent.");

        return (k - 1) / 2;
    }

    private int leftChild(int k) {
        return k * 2 + 1;
    }

    private int rightChild(int k) {
        return k * 2 + 2;
    }

    public void add(E e) {
        data.addLast(e);

        siftUp(data.getSize() - 1);
    }

    // 上浮, 關注 parent, 防止越界 < 0 ;
    private void siftUp(int k) {

        while (k > 0) {

            if (data.get(parent(k)).compareTo(data.get(k)) < 0)
                data.swap(k, parent(k));
            k = parent(k);
        }
    }

    public E findMin() {
        if (data.getSize() == 0)
            throw new RuntimeException("Cannot findMin while Heap is empty.");

        return data.get(0);
    }

    public E extractMin() {

        E ret = findMin();

        data.swap(0, data.getSize() - 1);
        data.removeLast();

        siftDown(0);
        return ret;
    }

    public E replace(E e) {

        E ret = findMin();

        data.set(e, 0);
        siftDown(0);
        return ret;
    }
}
