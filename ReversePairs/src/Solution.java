import java.util.Arrays;

public class Solution {

    public int reversePairs(int[] nums) {

        /**
        int res = 0;
        for (int i = 0; i < nums.length; i++)
            for (int j = i + 1; j < nums.length; j++)
                if (nums[i] > nums[j]) res ++;
        return res;
        */
        // O(n^2) 級別, 超出時間限制

        int[] temp = Arrays.copyOf(nums, nums.length);
        return sortBU(nums, 0, nums.length - 1, temp);
    }

    // top-down mergeSort
    public static int sort(int[] arr, int l, int r, int[] temp, int depth) {

        int res = 0;

        String depthString = generateDepthString(depth);
        System.out.print(depthString);
        // 打印當前處理的區間
        System.out.printf("merge[%d, %d]", l, r);

        if (l >= r) return 0;

        int mid = l + (r - l) / 2;

        res += sort(arr, l, mid, temp, 0);
        res += sort(arr, mid + 1, r, temp, 0);

        // 打印merge 的區間
        System.out.print(depthString);
        System.out.printf("merge arr[%d, %d] and arr[%d, %d]", l, mid, mid + 1, r);

        // merge [l, mid] and [mid + 1, r]
        if (arr[mid] > arr[mid + 1])
            res += merge(arr, l, mid, r, temp);

        // 打印merge 後的數組
        System.out.print(depthString);
        System.out.printf("after merge sort[%d, %d]", l, r);
        for (int e: arr) {
            System.out.print(e+ " ");
        }
        System.out.println();
        return res;
    }

    public static int sortBU(int[] nums, int l, int r, int[] temp) {

        // the length of arr
        int n = nums.length, res = 0;

        // sort
        for (int sz = 1; sz <= r; sz += sz) {

            // merge [i, i + sz - 1] and [i + sz, Math.min(i + sz + sz, n - 1)]
            for (int i = 0; i + sz < n; i += sz + sz) {
                if (nums[i + sz - 1] > nums[i + sz])
                    res += merge(nums, i, i + sz - 1, Math.min(i + sz + sz - 1, n - 1), temp);
            }
        }
        return res;
    }

    public static int merge(int[] arr, int l, int mid, int r, int[] temp) {

        // make sure the elements in temp is same as in arr
        System.arraycopy(arr, l, temp, l, r - l + 1);

        // find the smallest element in [l, mid] and [mid + 1, r] respectively and merge
        // when to break out: i > mid ; j > r

        // setup index
        int i = l, j = mid + 1, res = 0;

        for (int k = l; k <= r; k++) {

            if (i > mid) {
                arr[k] = temp[j]; j++;
            } else if (j > r) {
                arr[k] = temp[i]; i++;
            } else if (temp[i] <= temp[j]) {
                arr[k] = temp[i]; i++;
            } else {
                res += mid - i + 1;
                arr[k] = temp[j]; j++;
            }
        }

        return res;
    }

    private static String generateDepthString(int depth) {

        StringBuilder res = new StringBuilder();
        for (int i = 0; i < depth; i ++) {
            res.append("--");
        }
        return res.toString();
    }

    public static void main(String[] args) {

        int[] nums = {1, 4, 3, 6, 5};

        System.out.println("res = " + (new Solution()).reversePairs(nums));
        for (int e: nums) {
            System.out.print(e + " ");
        }
    }
}
