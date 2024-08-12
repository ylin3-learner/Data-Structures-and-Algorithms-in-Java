public class BinarySearch {

    private BinarySearch() {}

    // 非遞歸 實現二分查找法
    public static <E extends Comparable<E>> int search(E[] arr, E target) {

        // Define search range of arr
        int l = 0, r = arr.length - 1;

        // loop invariant: always search between arr[l, r]
        // arr[l, r] 尋找 target
        while (l <= r) {

            // divide arr.length
            int mid = l + (r - l) / 2;

            if (arr[mid].compareTo(target) == 0)
                return mid;

            if (arr[mid].compareTo(target) < 0)
                l = mid + 1;  // 繼續在arr[mid+1, r] 範圍裡尋找解
            else
                // arr[mid] > target
                r = mid - 1;  // 繼續在arr[l, mid-1] 範圍裡尋找解
        }

        return -1;
    }

    // 查找 >= target 的最小值, 如果與 target 相等, 則找到 ; 反之，返回 -1
    public static <E extends Comparable<E>> int search2(E[] arr, E target) {

        // Define search range of arr
        int l = 0, r = arr.length;

        // loop invariant: always search between arr[l, r]
        // arr[l, r] 尋找 target
        // 以下代码是求解 >= target 的最小值索引
        while (l < r) {

            // divide arr.length
            int mid = l + (r - l) / 2;

            if (arr[mid].compareTo(target) < 0)
                l = mid + 1;  // 繼續在arr[mid+1, r] 範圍裡尋找解
            else
                // arr[mid] > target
                r = mid;  // 繼續在arr[l, mid-1] 範圍裡尋找解
        }
        // ----------
        // 求解 >= target 的最小值索引算法结束

        // 如果 data[l] == target，则返回 l；否则返回 -1
        // 注意，求解 >= target 的最小值索引，结果可能是 data.length，不是合法索引
        // 所以，我们要对 l 的合法性进行判断，即确定 l < data.length
        if(l < arr.length && arr[l].compareTo(target) == 0)
            return l;
        return -1;
    }

    public static <E extends Comparable<E>> int searchR(E[] arr, E target) {

        return searchR(arr, 0, arr.length - 1, target);
    }

    // 遞歸 實現二分查找法
    private static <E extends Comparable<E>> int searchR(E[] arr, int l, int r, E target) {

        // basic problem
        if (l > r) return -1;

        int mid = l + (r - l) / 2;

        if (arr[mid].compareTo(target) == 0)
            return mid;

        if (arr[mid].compareTo(target) < 0)
            return searchR(arr, mid + 1, r, target);

        return searchR(arr, l, mid - 1, target);
    }

    // > target 的最小值索引
    public static <E extends Comparable<E>> int upper(E[] arr, E target) {

        // 為何 r 是 arr.length　不是 arr.length - 1? 因為有可能要找的最小值索引都不在當前的 arr 中, 而是在 arr 之外
        // 因此保留 當前 arr 最後一個元素 + 1 來表示 要找的索引在當前 arr 之外
        int l = 0, r = arr.length;

        // 在 arr[l, r] 中尋找 > target 的最小值

        // 為何不是 l >= r ? 因為 l = r 就代表兩個指針的索引指向同一位置, 此時為找到我們需要的解
        while (l < r) {

            int mid = l + (r - l) / 2;

            if (arr[mid].compareTo(target) <= 0)
                l = mid + 1;
            else
                // r = mid ? 因為 當前的mid 為 > target 但不能確定是否為最小值, 因此不能過早丟棄
                r = mid;
        }

        return l;
    }

    // ceil 函數將十進制數轉換為直接最大整數 : ceil(5.0) = 5, ceil(5.5) = 6
    /*
       {1, 1, 3, 3, 5, 5, 7, 7}

       查找 5 : 如果數組存在元素, 返回最大索引, 此時返回 5 (即第二個 5 所在的位置)
       查找 6 : 如果數組不存在元素, 返回 > target 的最小索引 6 (即第一個 7 所在的位置)
     */
    public static <E extends Comparable<E>> int upper_ceil(E[] arr, E target) {

        int u = upper(arr, target);
        System.out.println("\ntarget = " + target);

        if (u - 1 >= 0 && arr[u-1].compareTo(target) == 0) {
            System.out.print("-- Return upper(" + u + ") ");
            return u - 1;
        }

        return u;

    }

    // lower_ceil
    /*
       {1, 1, 3, 3, 5, 5, 7, 7}

       查找 5 : 如果數組存在元素, 返回最大索引, 此時返回 最小索引, 此時為 4 (即第一個 5 所在的位置)
       查找 6 : 如果數組不存在元素, 返回 > target 最小索引, 此時返回 6 (即第一個 7 所在的位置)
     */

    // arr[l, r] 查找 >= target 的最小索引
    public static <E extends Comparable<E>> int lower_ceil(E[] arr, E target) {

        int l = 0, r = arr.length;

        while (l < r) {

            int mid = l + (r - l) / 2;

            // 在 upper 中，这里是 data[mid].compareTo(target) <= 0
            // 但是，对于 lower_ceil 来说，在 data[mid] == target 的时候，有可能是解
            // 也就是，data[mid] == target 的时候可能是解，也可能有更小的解在左边，应该去更新右边界
            if (arr[mid].compareTo(target) < 0)
                l = mid + 1;
            else
                r = mid;
        }

        return l;
    }

    // 查找 < target 的最大索引
    /*
    {1, 1, 3, 3, 5, 5}

    查找 3 : 存在元素, 返回 1 (第二個 1 所在的位置)
    查找 4 : 不存在元素, 返回 3 (第二個 3 所在的位置)
     */

    // search : arr[l, r]
    public static <E extends Comparable<E>> int lower(E[] arr, E target) {

        // Suppose : {1, 1, 3, 3, 5, 5}
        // Search 1 : return - 1 (the smallest element still not smaller than 1)
        // Search 10 : return 5 (the last element is the biggest index smaller than 10)
        int l = - 1, r = arr.length - 1;

        while (l < r) {

            // 當 l = 0, r = 1 --> mid = 0 + (1 - 0) / 2 = 0
            // 下一輪 l = mid = 0, r = 1 --> 結果 : 搜索空間沒有變化, 進入死循環 (也就是l 與 r 相鄰)
            // Sol 1 : 不夠優雅! -- int range = (r - l) / 2 == 0 ? (r - l) % 2 : (r - l) / 2;
            // Sol 2 : 當 l 與 r 相減為奇數時, 採用上取整方式, 避免 l = mid 陷入死循環
            int mid = l + (r - l + 1) / 2;

            // 打印跟蹤 - 結果 : 當 target == 2, 不斷輸出 0, 1
//            System.out.println(l + " " + r);

            if (arr[mid].compareTo(target) < 0)
                // 往 mid 右邊找, 但需要包含 mid
                l = mid;
            else
                // 往 mid 左邊找, 不須包含 mid
                r = mid - 1;
        }

        return l;
    }

    // lower_floor
    /*
    {1, 1, 3, 3, 5, 5, 7, 7}
    查找 5 : 如果數組中存在元素，返回最小索引(第一個 5 的位置)
    查找 4 : 如果不存在, 返回 lower() -小於 target 的最大索引 (第二個 3 的位置)
     */

    // search arr[l, r]
    public static <E extends Comparable<E>> int lower_floor(E[] arr, E target) {

        int p = lower(arr, target);

        if (p + 1 <= arr.length - 1 && arr[p+1].compareTo(target) == 0)
            return p + 1;
        return p;
    }

    /*
    upper_floor

    {1, 1, 3, 3, 5, 5, 7, 7}
    查找 5 : 如果數組中存在元素，返回最大索引(第二個 5 的位置)
    查找 4 : 如果不存在, 返回 lower() -小於 target 的最大索引 (第二個 3 的位置)
    如何用一句話概括? <= target 的最大索引
     */
    public static <E extends Comparable<E>> int upper_floor(E[] arr, E target) {

        int l = -1, r = arr.length - 1;

        while (l < r) {

            int mid = l + (r - l + 1) / 2;

            if (arr[mid].compareTo(target) <= 0)
                // 找右邊
                l = mid;
            else
                r = mid - 1;
        }

        return l;
    }

    public static void main(String[] args) {

        Integer[] arr = {1, 1, 3, 3, 5, 5};

        // 測試 target = 2, 是否進入死循環 ; 結果 : 進入死循環 !!
//        System.out.println(BinarySearch.lower(arr, 2));
        for (int i = 0; i <= 6; i++) {
            // 可能在 target == 2 時進入死循環
            System.out.print(BinarySearch.lower(arr, i) + " ");
        }

        System.out.println();

        System.out.print("lower_floor : ");
        for(int i = 0; i <= 6; i ++)
            System.out.print(BinarySearch.lower_floor(arr, i) + " ");
        System.out.println();

        System.out.print("upper_floor : ");
        for(int i = 0; i <= 6; i ++)
            System.out.print(BinarySearch.upper_floor(arr, i) + " ");
        System.out.println();

        int x = 6, y = 10;
        --y;
        x++;
        System.out.println(x++ * y--);
        System.out.println(++x / --y);
        System.out.printf("%d, %d", x, y);

    }
}
