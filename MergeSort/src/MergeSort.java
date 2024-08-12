import java.util.Arrays;

public class MergeSort {

    private MergeSort() {}

    // Top-Down MergeSort
    public static <E extends Comparable <E>> void sort(E[] arr) {
        sort(arr, 0, arr.length - 1, 0);
    }


    // 為何要在創建一個同名私有遞規函數? 為了給定sort 範圍
    private static <E extends Comparable<E>> void sort(E[] arr, int l, int r, int depth) {

        // 生成深度字符串
        String depthString = generateDepthString(depth);

        // 打印当前 sort 处理的数组区间信息
        System.out.print(depthString);
        System.out.printf("mergeSort[%d, %d]%n", l, r);

        // Check for stop recursion
        if (l >= r) return;

        // Switch to InsertionSort's O(n) with nearly "small ordered data"
        // 但是在腳本語言中, 對解析器而言則會耗時更久, 因為要調用另一個函數並傳參
        if (r - l <= 15) {
            InsertionSort.sort(arr, l, r);
            return;
        }

        // 确保在计算中间索引时不会发生整数溢出
        // int mid = (l + r) / 2; 溢出
        // int 类型，其范围是 -2^31 到 2^31-1。 假设 l 是 2^30，r 是 2^31 - 1。
        // 计算中间索引，这将等同于 (2^30 + 2^31 - 1) / 2，其结果是 3 * 2^30 - 1 超出了 int 类型的正数范围导致整数溢出
        int mid = l + (r - l) / 2;

        sort(arr, l, mid, depth + 1);
        sort(arr, mid + 1, r, depth + 1);

        // 打印merge 要归并的数组范围
        System.out.print(depthString);
        System.out.printf(String.format("merge arr[%d, %d] and arr[%d, %d]", l, mid, mid + 1, r));

        // If the elements at arr[mid] and arr[mid + 1] are already in the correct order (non-decreasing),
        // it means that the sub-arrays are sorted, and there is no need to merge them.
        if (arr[mid].compareTo(arr[mid + 1]) > 0)
            merge(arr, l, mid, r);

        // 打印 merge 后的数组，在 merge 后，我们具体打印出数组变成了什么样子。
        System.out.print(depthString);
        System.out.printf("after mergesort arr[%d, %d] :", l, r);
        for(E e: arr)
            System.out.print(e + " ");
        System.out.println();
    }

    // Bottom-up MergeSort
    public static <E extends Comparable<E>> void sortBU(E[] arr) {

        E[] temp = Arrays.copyOf(arr, arr.length);

        int n = arr.length;

        // 優化1: 調用InsertionSort 優化的原理, 當數據規模小且有序時, 可以降至O(n)
        // 假設 arr[i, i + 15] 使用 InsertionSort
        // 注意: r 需要判斷是否在不斷 i + 15 會越界, 使用 Math.min(n - 1, i + 15) 防止越界
        for (int i = 0; i < n; i += 16)
            InsertionSort.sort(arr, i, Math.min(n - 1, i + 15));

        // 遍歷合併的區間長度
        // 注意: After Optimized by InsertionSort, length of sz will become 16 instead of 1.
        // 問題: 16 這個數字怎麼來的? 經驗數字 (在執行算法前決定, 而不是根據算法推導)
        for (int sz = 16; sz < n; sz += sz) {

            // 遍歷合併的兩個區間長度 i
            // 合併 [i, i + sz - 1] 和 [i + sz, i + sz + sz - 1]
            // 如果是 i + sz 可以保證不越界, 但 i + sz + sz 可能越界, 所以要取邊界的最小值 Math.min(i + sz + sz - 1, n - 1)
            for (int i = 0; i + sz < n; i += sz + sz) {
                if (arr[i + sz - 1].compareTo(arr[i + sz]) > 0)
                    merge2(arr, i, i + sz - 1, Math.min(i + sz + sz - 1, n - 1), temp);
            }
        }
    }

    // 合併兩個有序的數組 arr[l, mid], arr[mid + 1, r]
    private static <E extends Comparable<E>> void merge(E[] arr, int l, int mid, int r) {

        // 為何 r + 1 ?
        // from – the initial index of the range to be copied, inclusive
        // to – the final index of the range to be copied, exclusive (前閉後開的區間)
        // The new array's index will lie between zero and original
        // --> arr[l, r] -> temp[i, j]
        E[] temp = Arrays.copyOfRange(arr, l, r + 1);

        // setup index
        int i = l, j = mid + 1;

        // 使用循環遍歷temp 為 arr[k] 賦值
        for (int k = l; k <= r ; k++) {

            // check for i's boundary
            if (i > mid) {
                arr[k] = temp[j - l]; j++;
            } else if (j > r) {
                arr[k] = temp[i - l]; i++;
                // 应该在 temp 数组上进行比较和赋值，而不是在原始的 arr 上。
            } else if (temp[i - l].compareTo(temp[j - l]) <= 0) {
                arr[k] = temp[i - l]; i++;
            } else {
                arr[k] = temp[j - l]; j++;
            }

            // compare arr[i], arr[j] 's value
        }
    }

    public static <E extends Comparable <E>> void sort2(E[] arr) {

        E[] temp = Arrays.copyOf(arr, arr.length);
        sort2(arr, 0, arr.length - 1, temp);
    }


    // 為何要在創建一個同名私有遞規函數? 為了給定sort 範圍
    private static <E extends Comparable<E>> void sort2(E[] arr, int l, int r, E[] temp) {

        // Check for stop recursion
        if (l >= r) return;

        // 優化2: Switch to Insertion sort's O(n) with nearly small ordered data
        // 但是在腳本語言中, 對解析器而言則會耗時更久, 因為要調用另一個函數並傳參
        if (r - l <= 15) {
            InsertionSort.sort(arr, l, r);
            return;
        }

        // 确保在计算中间索引时不会发生整数溢出
        // int mid = (l + r) / 2; 溢出
        // int 类型，其范围是 -2^31 到 2^31-1。 假设 l 是 2^30，r 是 2^31 - 1。
        // 计算中间索引，这将等同于 (2^30 + 2^31 - 1) / 2，其结果是 3 * 2^30 - 1 超出了 int 类型的正数范围导致整数溢出
        int mid = l + (r - l) / 2;

        sort2(arr, l, mid, temp);
        sort2(arr, mid + 1, r, temp);


        // 優化1: 只有到兩邊需要merge 時候[當mid > mid + 1], 才merge();
        if (arr[mid].compareTo(arr[mid + 1]) > 0)
            merge2(arr, l, mid, r, temp);

    }

    // 合併兩個有序的數組 arr[l, mid], arr[mid + 1, r]
    private static <E extends Comparable<E>> void merge2(E[] arr, int l, int mid, int r, E[] temp) {

        // 為何 r + 1 ?
        // from – the initial index of the range to be copied, inclusive
        // to – the final index of the range to be copied, exclusive (前閉後開的區間)
        // The new array's index will lie between zero and original
        // --> arr[l, r] -> temp[i, j]
//        E[] temp = Arrays.copyOfRange(arr, l, r + 1);

        // 優化3: 當每一次call merge() 時, 都需要在內存中不斷開闢temp 的空間, 將會耗時就如同ListNode new Node()
        // 解決:
        // 使用公共的temp 在一開始調用sort2() 中,
        // 並在merge() 中調用System.arraycopy() 保證temp 與　arr 中的元素及索引保持一致
        // 這時, temp 將跟 arr 索引一樣從 l 開始, 就不需減去 l 偏移量
        System.arraycopy(arr, l, temp, l, r - l + 1);


        // setup index
        int i = l, j = mid + 1;

        // 使用循環遍歷temp 為 arr[k] 賦值
        for (int k = l; k <= r ; k++) {

            // check for i's boundary
            if (i > mid) {
                arr[k] = temp[j]; j++;
            } else if (j > r) {
                arr[k] = temp[i]; i++;
                // 应该在 temp 数组上进行比较和赋值，而不是在原始的 arr 上。
            } else if (temp[i].compareTo(temp[j]) <= 0) {
                arr[k] = temp[i]; i++;
            } else {
                arr[k] = temp[j]; j++;
            }

            // compare arr[i], arr[j] 's value
        }
    }


    // Print out recursion layers
    private static String generateDepthString(int depth) {

        StringBuilder res = new StringBuilder();
        for (int i = 0; i < depth; i++)
            res.append("--");
        return res.toString();
    }

    public static void main(String[] args) {

//        Integer[] data = {10000, 100000};
//        for (int i: data) {
//            Integer[] arr = ArrayGenerator.generateRandomArray(i, i);
//            SortingHelper.sortTest("MergeSort", arr);
//            SortingHelper.sortTest("InsertionSort", arr);
//        }

        Integer[] arr = {7, 1, 4, 2, 8, 3, 6, 5};
        Integer[] arr2 = Arrays.copyOf(arr, arr.length);
        MergeSort.sort(arr, 0, arr.length - 1, 0);
        System.out.println();
        SortingHelper.sortTest("MergeSort", arr);
        SortingHelper.sortTest("MergeSortBU", arr2);
    }
}
