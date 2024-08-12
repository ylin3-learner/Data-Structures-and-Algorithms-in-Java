import java.util.Random;

public class ArrayGenerator {

    private ArrayGenerator() {}

    public static Integer[] generateOrderedArray(int n) {

        Integer[] arr = new Integer[n];
        for (int i = 0; i < n; i++) {
            arr[i] = i;
        }
        return arr;
    }

    public static Integer[] generateRandomArray(int n, int bound) {

        Integer[] arr = new Integer[n];

        Random rnd = new Random();
        for (int i = 0; i < n; i++) {
            arr[i] = rnd.nextInt(bound);
        }
        return arr;
    }

    // Which can make the worst case of O(n ^ 2)
    public static Integer[] generateSpecialArray(int n, int depth){

        // 开空间
        Integer[] arr = new Integer[n];

        // 生成 arr[0...n-1] 的测试用例，其中最小值是 0
        generateSpecialArray(arr, 0, arr.length - 1, 0, depth + 1);

        return arr;
    }

    // Put the smallest element in the middle, and the secondly-smallest element in the rest of middle
    private static void generateSpecialArray(Integer[] arr, int l, int r, int value, int depth) {

        String depthString = generateDepthString(depth);
        System.out.print(depthString);
        System.out.printf("Special Array[%d, %d], minValue: %d\n", l, r, value);

        // 递归到底的情况。如果 l > r，我们要处理的区间为空，直接返回
        // Why don't we need to tackle l = r case? Because in this case, we are right at the last index to insert in the mid-position in the last call.
        if(l > r) return;

        // 1. 把最小值放到中间
        int mid = l + (r - l) / 2;
        arr[mid] = value;

        // 2. 模拟 partition 过程，把中间元素和最左边元素交换位置；
        swap(arr, l, mid);

        // 3. 处理除了最左边的元素之外，剩下的 n - 1 个元素；
        // 所以，处理的区间变成了 arr[l+1...r]，同时，最小值 + 1

        System.out.print(depthString);
        System.out.printf("swap arr[%d, %d]", l, r);

        generateSpecialArray(arr, l + 1, r, value + 1, depth + 1);

        // 4. 都处理好以后，还要把中间的元素和最左边的元素交换回来。
        // Why would you need swap() again? Because we insert smallest value first; then, we need to swap back into the mid-position
        swap(arr, l, mid);

        System.out.print(depthString);
        System.out.printf("After swap arr[%d, %d], minValue: %d -> ", l, r, value);

        for (int e: arr) {
            System.out.print(e +" ");
        }
        System.out.println();
    }

    private static <E> void swap(E[] arr, int l, int r) {
        E temp = arr[l];
        arr[l] = arr[r];
        arr[r] = temp;
    }

    private static String generateDepthString(int depth) {

        StringBuilder res = new StringBuilder();

        for (int i = 0; i < depth; i++) {
            res.append("--");
        }
        return res.toString();
    }
}
