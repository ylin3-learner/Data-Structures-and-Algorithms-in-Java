import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Solution {

    // target(int) : [] (unsure numbers -> ArrayList)
    // Statically Fair == equal probability [https://stackoverflow.com/questions/10984974/why-do-people-say-there-is-modulo-bias-when-using-a-random-number-generator]
    private HashMap<Integer, ArrayList<Integer>> map;
    private Random rnd;

    public Solution(int[] nums) {

        map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {

            if (!map.containsKey(nums[i]))
                map.put(nums[i], new ArrayList<>());
            map.get(nums[i]).add(i);
        }

        rnd = new Random();
    }

    public int pick(int target) {

        // According to LC, you can assume that the given target number must exist in the array.
        // If not, null pointer "map.get(target)" handling
        int sz = map.get(target).size();
        int randomIndex = rnd.nextInt(sz);  // Prevent modulo bias
        // int randomIndex = (int)(rnd.nextDouble() * sz);
        return map.get(target).get(randomIndex);
    }
}
