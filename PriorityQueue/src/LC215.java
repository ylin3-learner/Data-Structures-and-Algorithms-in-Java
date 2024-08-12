import java.util.PriorityQueue;
public class LC215 {

    // 在面试中，通常更推荐使用 QuickSort() 来找到第 K 小的元素。
    /*
    QuickSort : Time : O(n) ; Space : O(1)
    PriorityQueue : Time : O(nlogK) ; Space : O(k)

    // !!! PriorityQueue's advantage : 1. 不需要一次性知道所有數據 -> 數據流 (實時求解) e.g. 更新排名 2. 快排 : 數據規模極大, 不會塞爆內存

     */
    // 使用最小堆的时间复杂度通常为 O(NlogK)
    /*
    时间复杂度分析如下：

        初始化堆：初始化堆的时间复杂度为 O(K)。
        遍历剩余元素：遍历剩余的元素的时间复杂度为 O(N-K)，其中 N 为数组的长度。
        对于每个元素，执行插入和调整操作：在最小堆中插入一个元素的时间复杂度为 O(logK)，因为堆的高度为 logK，调整堆的时间复杂度也是 O(logK)。
        因此，总的时间复杂度为 (O(K) + O(N-K) ) * O(logK)。在 N 很大的情况下，通常可以近似为 O(NlogK)，因为 N 远大于 K。
     */
    public int findKthLargest(int[] nums, int k) {

        // Java PriorityQueue is made of minHeap.
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        for (int i = 0; i < k; i++)
            priorityQueue.add(nums[i]);

        for (int i = k; i < nums.length; i++) {
            if (!priorityQueue.isEmpty() && nums[i] > priorityQueue.peek()) {
                priorityQueue.remove();
                priorityQueue.add(nums[i]);
            }
        }

        return priorityQueue.peek();
    }
}
