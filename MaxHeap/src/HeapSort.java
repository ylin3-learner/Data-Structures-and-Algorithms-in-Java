/*
為何算法類 都不喜歡用戶創建其對象 而是直接調用?
1. 解耦和模块化：算法类 其目的是执行特定的算法逻辑。如果算法类直接依赖于用户创建的对象，那么算法类与用户对象之间就会存在耦合
2. 职责分离：算法类的职责是执行特定的算法逻辑，而不是负责创建用户对象。
3. 依赖倒置原则：算法类应该依赖于抽象而不是具体实现。
 */

import java.util.Arrays;

public class HeapSort {

    private HeapSort() {}

    public static <E extends Comparable<E>> void sort(E[] data) {

        MaxHeap<E> maxHeap = new MaxHeap<>();
        for (E e: data)
            // add() : log^n -> n elements -> Total Time cost : nlog^n
            maxHeap.add(e);

        // Default data ordered - from left to right , from small to large
        for (int i = data.length - 1; i >= 0; i--)
            // extractMax() : log^n -> n elements -> Total Time cost : nlog^n
            data[i] = maxHeap.extractMax();
    }

    // 原地堆排序
    /*
    1. 將 E[] arr 先變成 MaxHeap (from large to small) : parent() + siftDown()
    2. 將 MaxHeap 第一個元素與倒數第一個元素交換, 製造由小到大
    3. 將被交換過的 MaxHeap 部分, 重新執行 siftDown(), 在製造一次 MaxHeap
    4. 重複執行 1 - 3
     */
    public static <E extends Comparable<E>> void sort2(E[] data) {

        // Check if data.length == 1 ; Namely, only has one element
        if (data.length <= 1) return;

        // Transform arr into MaxHeap
        // parent(data.length - 1) == (data.length - 1) - 1 / 2
        for (int i = (data.length - 2) / 2; i >= 0; i--)
            siftDown(data, i, data.length);

        for (int i = data.length - 1; i >= 0; i--) {
            swap(data, 0, i);
            siftDown(data, 0, i);
        }
    }
    // parent: 1, leftChild : 4 ; parent : 2, leftChild : 5
    // 對 data[0, n) 形成的 MaxHeap , 索引 K 執行 最大堆
    public static<E extends Comparable<E>> void  siftDown(E[] data, int k, int n) {

        // leftChild(k) = k * 2 + 1 ; n = data.length
        while (k * 2 + 1 < n) {

            // Choose the bigger one from leftChild() and rightChild()
            int j = k * 2 + 1;
            if (j + 1 < n && data[j].compareTo(data[j + 1]) < 0)
                j ++;

            if (data[k].compareTo(data[j]) >= 0)
                break;

            swap(data, k, j);
            k = j;
        }
    }

    public static<E> void swap(E[] data, int i, int j) {
        E temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

    public static void main(String[] args) {

        int n = 1000000;
        Integer[] arr = ArrayGenerator.generateRandomArray(n, n);
        Integer[] arr2 = Arrays.copyOf(arr, arr.length);
        Integer[] arr3 = Arrays.copyOf(arr, arr.length);
        Integer[] arr4 = Arrays.copyOf(arr, arr.length);
        Integer[] arr5 = Arrays.copyOf(arr, arr.length);


        SortingHelper.sortTest("HeapSort", arr);
        SortingHelper.sortTest("QuickSort2Ways", arr2);
        SortingHelper.sortTest("QuickSort3Ways", arr3);
        SortingHelper.sortTest("MergeSort", arr4);
        SortingHelper.sortTest("HeapSort2", arr5);
    }
}
