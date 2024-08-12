import java.util.Arrays;

public class ShellSort {

    private ShellSort() {
    }

    public static <E extends Comparable<E>> void sort(E[] data) {

        int h = data.length / 2;
        while (h >= 1) {
            // 當 h == 1 時, 為最後一次插入排序 (間隔為 1)


            // 下方 ShellSort Complexity : n^2/1 + n^2/2 + ... + n^2/(n/2, when h == n / 2)
            // n^2 (1/1 + 1/2 + 1/4 + ... + 1/(n/2) ) = n^2 (1 - 1/2 ^ log^n / 1 - 1/2 )
            // O(n^2 - n^2 / 2 ^ log^n)

            // 總共有幾個小數組? h 個 , 因為共有 0 ~ h - 1 個數組的第一個元素在 start + h 前面
            // h 輪
            for (int start = 0; start < h; start++) {

                // 對 data[start, start + h, start + 2h, start + 3h, ...] 進行排序
                // InsertionSort : (n / h) ^ 2 輪
                for (int i = start + h; i < data.length; i += h) {

                    E temp = data[i];
                    int j;
                    for (j = i; j - h >= 0 && temp.compareTo(data[j - h]) < 0; j -= h)
                        data[j] = data[j - h];
                    data[j] = temp;
                }
            }

            h /= 2;
        }
    }

    public static<E extends Comparable<E>> void sort2(E[] data) {

        int h = data.length / 2;
        while (h >= 1) {
            // 當 h == 1 時, 為最後一次插入排序 (間隔為 1)


            // 下方 ShellSort Complexity : n^2/1 + n^2/2 + ... + n^2/(n/2, when h == n / 2)
            // n^2 (1/1 + 1/2 + 1/4 + ... + 1/(n/2) ) = n^2 (1 - 1/2 ^ log^n / 1 - 1/2 )
            // O(n^2 - n^2 / 2 ^ log^n)

            // 對 data[h, n) 進行插入排序
            // 也就是對每一個小數組的第二個元素 依序進行插入排序, 而不是先處理完一個小數組再處理下一個

            // 總共: n^2/h
            for (int i = h; i < data.length; i += h) {  // n - h 個元素, h 視為常數, 因此 大約為 n 個元素

                    E temp = data[i];
                    int j;
                    for (j = i; j - h >= 0 && temp.compareTo(data[j - h]) < 0; j -= h)  // n / h 次比較
                        data[j] = data[j - h];
                    data[j] = temp;
                }
            h /= 2;
        }
    }

    public static<E extends Comparable<E>> void sort3(E[] data) {

        int h = 1;

        while (h < data.length) {
            h = h * 3 + 1;
            System.out.println("h1: " + h);
        }
        // 1, 4, 13, 40...

        if (h > data.length) System.out.println("h1 out of data.length!, h1: " + h);

        while (h >= 1) {
            // 當 h == 1 時, 為最後一次插入排序 (間隔為 1)


            // 下方 ShellSort Complexity : n^2/1 + n^2/2 + ... + n^2/(n/2, when h == n / 2)
            // n^2 (1/1 + 1/2 + 1/4 + ... + 1/(n/2) ) = n^2 (1 - 1/2 ^ log^n / 1 - 1/2 )
            // O(n^2 - n^2 / 2 ^ log^n)

            // 對 data[h, n) 進行插入排序
            // 也就是對每一個小數組的第二個元素 依序進行插入排序, 而不是先處理完一個小數組再處理下一個

            // 總共: n^2/h
            for (int i = h; i < data.length; i += h) {  // n - h 個元素, h 視為常數, 因此 大約為 n 個元素

                E temp = data[i];
                int j;
                for (j = i; j - h >= 0 && temp.compareTo(data[j - h]) < 0; j -= h)  // n / h 次比較
                    data[j] = data[j - h];
                data[j] = temp;
            }

            if (h < data.length) {
                System.out.println("h2: " + h);
            } else {
                System.out.println("h2 out of data.length!, h2: " + h);
            }

            h /= 3;
        }
    }


    public static void main(String[] args) {

        int n = 10;

        Integer[] arr = ArrayGenerator.generateRandomArray(n, n);
//        Integer[] arr2 = Arrays.copyOf(arr, arr.length);
//        Integer[] arr3 = Arrays.copyOf(arr, arr.length);
//        Integer[] arr4 = Arrays.copyOf(arr, arr.length);
//
//
//        System.out.println("Random Array: ");
//        SortingHelper.sortTest("ShellSort", arr);
//        SortingHelper.sortTest("MergeSort", arr2);
//        SortingHelper.sortTest("QuickSort2Ways", arr3);
//        SortingHelper.sortTest("QuickSort3Ways", arr4);
//
//        Integer[] data = ArrayGenerator.generateRandomArray(n, n);
//        Integer[] data2 = Arrays.copyOf(data, data.length);
//        Integer[] data3 = Arrays.copyOf(data, data.length);
//        Integer[] data4 = Arrays.copyOf(data, data.length);
//
//        System.out.println();
//        System.out.println("Ordered Array: ");
//        SortingHelper.sortTest("ShellSort", data);
//        SortingHelper.sortTest("MergeSort", data2);
//        SortingHelper.sortTest("QuickSort2Ways", data3);
//        SortingHelper.sortTest("QuickSort3Ways", data4);
        ShellSort.sort3(arr);
    }
}
