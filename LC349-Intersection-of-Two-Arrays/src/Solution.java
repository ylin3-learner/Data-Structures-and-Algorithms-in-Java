import java.util.ArrayList;
import java.util.TreeSet;  // Set base on Search Tree

public class Solution {

    public int[] intersection(int[] nums1, int[] nums2) {

        TreeSet<Integer> set = new TreeSet<>();
        // Remove Duplicated element in nums1
        for (int num: nums1)
            set.add(num);

        // Initialize a list to contain unique intersections of num1 & num2
        ArrayList<Integer> list = new ArrayList<>();
        for (int num: nums2) {
            if (set.contains(num)) { // find intersection
                list.add(num);
                // Make sure each key is unique
                set.remove(num);
            }
        }

        int[] res = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            res[i] = list.get(i);
        }
        return res;
    }
}
