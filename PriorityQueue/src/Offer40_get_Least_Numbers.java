import java.util.Collections;
import java.util.PriorityQueue;
public class Offer40_get_Least_Numbers {

    // 在 nums 中 找出最小的 k 個數
    // 使用最大堆 : 因為要比較當前看到的最小 K 個元素的最大值 與 新的數據進行比較
    public static int[] getLeastNumbers(int[] nums, int k) {

        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());
        for (int i = 0; i < k; i ++)
            // 當前看到的前 K 個元素 的最小 k 個元素
            pq.add(nums[i]);

        for (int i = k; i < nums.length; i++) {
            // 如果比這 K 個 最小元素中最大的還小, 則替換
            // 因為要比較最小元素中的最大值, 使用最大堆
            if (!pq.isEmpty() && nums[i] < pq.peek()) {
                pq.remove();
                pq.add(nums[i]);
            }
        }

        int[] res = new int[k];
        for (int i = 0; i < pq.size(); i++)
            res[i] = pq.remove();

        return res;
    }

    public static void main(String[] args) {
        int[] arr = {3, 2, 1};
        int k = 2;
        int[] res = getLeastNumbers(arr, k);
        for (int num: res) {
            System.out.println(num+" ");
        }
    }
}

/*

// Comparable :
Comparable is an interface defining a strategy of comparing an object with other objects of the same type.
This is called the class’s “natural ordering.”
///
public class Player implements Comparable<Player> {

    // same as before

    @Override
    public int compareTo(Player otherPlayer) {
        return Integer.compare(getRanking(), otherPlayer.getRanking());
    }

}
///

// Comparator :
The Comparator interface defines a compare(arg1, arg2) method
///
public class PlayerRankingComparator implements Comparator<Player> {

    @Override
    public int compare(Player firstPlayer, Player secondPlayer) {
       return Integer.compare(firstPlayer.getRanking(), secondPlayer.getRanking());
    }

}

PlayerRankingComparator playerComparator = new PlayerRankingComparator();
Collections.sort(footballTeam, playerComparator);
///

There are several reasons why:

Sometimes we can’t modify the source code of the class whose objects we want to sort, thus making the use of Comparable impossible
Using Comparators allows us to avoid adding additional code to our domain classes
We can define multiple different comparison strategies, which isn’t possible when using Comparable
 */