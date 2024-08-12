import java.util.Arrays;

public class BubbleSort {

    private BubbleSort() {}

    public static <E extends Comparable<E>> void sort(E[] data) {

        // Why don't we need n times loop? Because it works to compare two objects
        // In the last run, it only remains one object in E[] data.
        for (int i = 0; i + 1 < data.length; i++) {

            // arr[n - i, n) 已排好序
            // 通過冒泡 在 arr[n - i - 1] 放上合適的元素

            // j, j + 1
            for (int j = 0; j + 1 <= data.length - i - 1; j++) {
                if (data[j].compareTo(data[j + 1]) > 0)
                    swap(data, j, j + 1);
            }
        }
    }

    public static <E extends Comparable<E>> void sort2(E[] data) {

        // Why don't we need n times loop? Because it works to compare two objects
        // In the last run, it only remains one object in E[] data.

        boolean isSwapped = false;
        for (int i = 0; i + 1 < data.length; i++) {

            // arr[n - i, n) 已排好序
            // 通過冒泡 在 arr[n - i - 1] 放上合適的元素

            // j, j + 1
            for (int j = 0; j + 1 <= data.length - i - 1; j++) {
                if (data[j].compareTo(data[j + 1]) > 0) {
                    swap(data, j, j + 1);
                    isSwapped = true;
                }
            }

            // 完全有序, 直接 break
            if (!isSwapped) break;
        }
    }

    public static <E extends Comparable<E>> void sort3(E[] data) {

        // Why don't we need n times loop? Because it works to compare two objects
        // In the last run, it only remains one object in E[] data.

        int lastSwappedIndex = 0;

        // 為何需要 i + 1 < data.length? 因為, 這代表將忽略最後一輪只有一個元素的情況, 此時就不需要冒泡交換元素
        for (int i = 0; i + 1 < data.length; ) {

            // arr[n - i, n) 已排好序
            // 通過冒泡 在 arr[n - i - 1] 放上合適的元素

            // j, j + 1
            for (int j = 0; j + 1 <= data.length - i - 1; j++) {
                if (data[j].compareTo(data[j + 1]) > 0) {
                    swap(data, j, j + 1);
                    lastSwappedIndex = j + 1;
                }
            }

            // 完全有序, 直接 break
//            if (lastSwappedIndex == 0) break;

            // 因為 lastSwappedIndex 代表交換結束的位置, 例如: 0 - 1 交換, 結束位置就是1
            // 我們知道 i 可以代表 1. 進行交換的輪數 2. i 也可以代表排序結束後, 每一輪的有序個數。 因此, i 可以代表最後有多少元素有序
            // 也就是 從 arr[lastSwappedIndex, n] 已經有序, 因此, 只需要在遍歷 arr[0, n - lastSwappedIndex] 的無序部分

            // 同時, 上方 if (lastSwappedIndex == 0) break; 已經不需要。因為, 如果 i = data.length - lastSwappedIndex (此時為0) 代表全部有序
            // 此時, i < data.length 就會違反跳出循環
            i = data.length - lastSwappedIndex;
        }
    }

    // 從後向前遍歷, 保證 arr[0] 為最小
    public static<E extends Comparable<E>> void sort4(E[] data) {

        // Backward iterating arr : arr[0, i)
        for (int i = 0; i + 1 < data.length; ) {

            // j, j - 1
            // arr[i] 放入適當的元素
            // boolean isSwapped = false;
            int lastSwappedIndex = data.length - 1;
            for (int j = data.length - 1; j > i; j--) {
                if (data[j-1].compareTo(data[j]) > 0) {
                    swap(data, j-1, j);
                    // isSwapped = true;
                    lastSwappedIndex = j - 1;
                }

                // 有序 : arr[0, lastSwappedIndex], 此時, i : 表示多少個元素已經有序!
                // 此時, 怎麼表示多少個元素已經有序? lastSwappedIndex + 1
                i = lastSwappedIndex + 1;
            }

//            if (!isSwapped) break;
//            if (lastSwappedIndex == data.length - 1) break;


        }
    }

    private static<E extends Comparable<E>> void swap(E[] data, int i, int j) {

        E temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

    public static void main(String[] args) {

        int n = 100000;
        System.out.println("Random Array: ");
        Integer[] arr = ArrayGenerator.generateRandomArray(5, 5);
//        Integer[] arr2 = Arrays.copyOf(arr, arr.length);
//        Integer[] arr3 = Arrays.copyOf(arr, arr.length);
//
//
//        SortingHelper.sortTest("BubbleSort", arr);
//        SortingHelper.sortTest("BubbleSort2", arr2);
//        SortingHelper.sortTest("BubbleSort3", arr3);
//
//        System.out.println();
//
//        System.out.println("Ordered Array: ");
//        arr = ArrayGenerator.generateOrderedArray(n);
//        arr2 = Arrays.copyOf(arr, arr.length);
//        SortingHelper.sortTest("BubbleSort", arr);
//        SortingHelper.sortTest("BubbleSort2", arr2);
//        SortingHelper.sortTest("BubbleSort3", arr3);

        SortingHelper.sortTest("BubbleSort4", arr);
    }
}
