public class Solution {


    // 當前需使用最大堆
    public int[] getLeastNumbers(int[] arr, int k) {

        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        // 假設當前的 arr[0], arr[k] 為 arr 中最小的元素
        for (int i = 0; i < k; i++)
            // 堆中的元素为数组中前 K 小的元素
            priorityQueue.enqueue(arr[k]);

        // 如果 後方元素比當前對列中的最大元素還小, 將隊首元素丟掉, 加入新的後方元素

        // 当遍历完整个数组后，堆顶元素即为第 K 小的元素。
        // 須注意上方 priorityQueue 中有元素, 即 k = 0 條件
        // 虽然堆本身是最大堆，但是堆顶元素是堆中最大的元素，也就是数组中前 K 小的元素中最大的一个。
        for (int i = k; i < arr.length; i++) {
            if (!priorityQueue.isEmpty() && arr[i] < priorityQueue.getFront()) {
                priorityQueue.dequeue();
                priorityQueue.enqueue(arr[i]);
            }
        }

        // 返回 int[]
        int[] res = new int[k];
        for (int i = 0; i < k; i++)
            res[i] = priorityQueue.dequeue();

        return res;
    }

    public int[] getBiggestNumbers(int[] arr, int k) {

        MinHeap<Integer> minHeap = new MinHeap<>();
        for (int i = 0; i < k; i ++)
            minHeap.add(arr[i]);

        for (int i = k; i < arr.length; i++) {
            if (!minHeap.isEmpty() && arr[i] > minHeap.findMin()) {
                minHeap.extractMin();
                minHeap.add(arr[i]);
            }
        }

        // 返回 int[]
        int[] res = new int[k];
        for (int i = 0; i < k; i++)
            res[i] = minHeap.extractMin();

        return res;
    }
    // [1, 3, 2, 4, 5], k = 1 (第二大)
    // minHeap : [1, 3]
    // After Comparison - minHeap : [1, 3] -> [2, 3] -> [4, 3] -> [4, 5]
    public int findKthLargest(int[] nums, int k) {

        // 假設 nums[0, k] 為 nums中最大的 放入最小堆
        MinHeap<Integer> minHeap = new MinHeap<>();
        for (int i = 0; i < k; i++)
            minHeap.add(nums[i]);

        for (int i = k; i < nums.length; i++) {
            if (nums[i] > minHeap.findMin())
                minHeap.replace(nums[i]);
        }

        return minHeap.findMin();
    }
}
