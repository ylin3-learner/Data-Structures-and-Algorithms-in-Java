import java.util.Random;

public class MinHeap <E extends Comparable<E>> {

    private Array<E> data;

    public MinHeap(int capacity) {
        data = new Array<>(capacity);
    }

    public MinHeap() {
        data = new Array<>();
    }

    public MinHeap(E[] arr) {

        data = new Array<>(arr);

        if (arr.length != 1) {
            for (int i = parent(getSize() - 1); i >= 0; i--) {
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

    // parent(1) = 0, parent(2) = 0, parent(3) = 1
    private int parent(int k) {

        if (k == 0)
            throw new IllegalArgumentException("Index-0 doesn't have parents.");
        return (k - 1) / 2;
    }

    public int leftChild(int k) {

        return k * 2 + 1;
    }

    public int rightChild(int k) {

        return k * 2 + 2;
    }

    public void add(E e) {
        data.addLast(e);
        siftUp(data.getSize() - 1);
    }

    private void siftUp(int k) {

        while (k > 0 && data.get(k).compareTo(data.get(parent(k)))< 0) {
            data.swap(k, parent(k));
            k = parent(k);
        }
    }

    // 看堆中最小元素
    private E findMin() {
        if (data.getSize() == 0)
            throw new RuntimeException("Cannot findMin while Heap is empty.");
        return data.get(0);
    }

    // 取出堆中最小元素
    public E extractMin() {
        E ret = findMin();

        data.swap(0, data.getSize() - 1);
        data.removeLast();

        siftDown(0);
        return ret;
    }

    private void siftDown(int k) {

        while (leftChild(k) < data.getSize()) {

            int j = leftChild(k);
            if (j + 1 < data.getSize() && data.get(j).compareTo(data.get(j + 1)) > 0)
                j ++;

            if (data.get(k).compareTo(data.get(j)) <= 0)
                break;

            data.swap(k, j);

            k = j;
        }
    }

    // 取出堆中的最小元素，并且替换成元素e
    public E replace(E e) {

        E ret = findMin();
        data.set(e, 0);

        siftDown(0);
        return ret;
    }

    public static void main(String[] args) {

        int n = 1000000;

        Integer[] testData = new Integer[n];

        Random random = new Random();
        for(int i = 0 ; i < n ; i ++)
            testData[i] = (random.nextInt(Integer.MAX_VALUE));

        MinHeap<Integer> minHeap = new MinHeap<>(testData);

        // Test if data is organized
        int[] arr = new int[testData.length];
        for (int i = 0; i < testData.length; i++)
            arr[i] = minHeap.extractMin();

        for(int i = 1 ; i < n ; i ++)
            if(arr[i-1] > arr[i])
                throw new IllegalArgumentException("Error");

        System.out.println("Test MinHeap completed.");
    }
}
