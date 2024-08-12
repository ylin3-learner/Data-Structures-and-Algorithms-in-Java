import java.util.ArrayList;
import java.util.TreeMap;

public class Solution {
    // 假設 nums1 = [1, 2, 2, 3]
    public int[] intersect(int[] nums1, int[] nums2) {

        // TreeMap<Element, Frequency of Element> - map contains frequency
        // map 將會被建立為 {1: 1, 2: 2, 3: 1}。
        TreeMap<Integer, Integer> map = new TreeMap<>();
        for (int num: nums1) {
            if (map.containsKey(num))
                map.put(num, map.get(num) + 1);
            else
                map.put(num, 1);
        }

        // ArrayList 存儲兩個陣列的交集元素。
        // 假設 nums2 = [2, 2, 3, 4]
        // list 將會包含 [2, 2, 3]
        // map 將會更新為 {1: 1}
        ArrayList<Integer> list = new ArrayList<>();
        for (int num: nums2) {
            if (map.containsKey(num)) {

                list.add(num);
                map.put(num, map.get(num) - 1);

                if (map.get(num) == 0)
                    map.remove(num);
            }
        }

        // 將 list 中的元素轉換為整數陣列 res，並返回該陣列作為方法的結果。
        int[] res = new int[list.size()];
        for (int i = 0; i < list.size(); i ++) {
            res[i] = list.get(i);
        }

        return res;
    }
}
