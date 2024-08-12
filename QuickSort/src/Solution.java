public class Solution {

    // three colors: {red : 0, white : 1, blue : 2};

    /*
     * Example 1:
     * Input: nums = [2,0,2,1,1,0]
     * Output: [0,0,1,1,2,2]
     */

    public void sortColors(int[] nums) {

        // By using QuickSort3Ways' partition idea - three areas

        // Suppose arr[l, r] -- we can use '1' as pivot : 0 is less than pivot, and 2 is greater than pivot on the other hand.
        // loop invariant : arr[0, zero] == 0, arr[zero + 1, i - 1] = 1, arr[i, n - 1] = 2
        int zero = -1, i = 0, two = nums.length;

        // iterate arr with three cases
        /*
        < v : swap(arr, i, lt); lt ++, i ++;
        > v : swap(arr, i, gt); gt --;
        == v : i ++;
         */

        while (i < two) {

            if (nums[i] == 0) {
                zero ++;
                swap(nums, i, zero);
                i ++;
            } else if (nums[i] == 2) {
                two --;
                swap(nums, i, two);
            } else {  // nums[i] == 0
                i ++;
            }
        }

        // Why don't we need swap(arr, i, zero) here?
        // Because we didn't randomly pick one element as pivot and put it as the first element, which asks us to move back l in the mid-array in the end.
    }

    public void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    public static void main(String[] args) {
        int[] nums = {2,0,1};

        new Solution().sortColors(nums);

        for (int e: nums) {
            System.out.print(e + " ");
        }
    }
}
