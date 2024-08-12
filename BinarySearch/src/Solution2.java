import java.util.Random;

class Solution2 {

    public int findKthLargest(int[] nums, int k) {

        Random rnd = new Random();
        return selectK(nums, nums.length - k, rnd);
    }

    // 在 arr[l, r] 尋找第 k 小的數字
    private int selectK(int[] arr, int k, Random rnd){

        int l = 0, r = arr.length - 1;

        while(l <= r){
            // partition 處理的是前閉後閉的區間
            int p = partition(arr, l, r, rnd);

            if(k == p) return arr[p];

            if(k < p)
                r = p - 1;
            else
                l = p + 1;
        }

        throw new RuntimeException("No Solution");
    }

    private int partition(int[] arr, int l, int r, Random rnd){

        // 生成 [l, r] 之间的随机索引
        int p = l + rnd.nextInt(r - l + 1);
        swap(arr, l, p);

        // arr[l+1...i-1] <= v; arr[j+1...r] >= v
        int i = l + 1, j = r;
        while(true){

            while(i <= j && arr[i] < arr[l])
                i ++;

            while(j >= i && arr[j] > arr[l])
                j --;

            if(i >= j) break;

            swap(arr, i, j);

            i ++;
            j --;
        }

        swap(arr, l, j);
        return j;
    }

    private void swap(int[] arr, int i, int j){

        int t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }

    public static void main(String[] args) {
        int[] arr = {1, 4, 2, 3, 5};
        int k = 2;
        System.out.println(k + "th Largest element in arr : " + (new Solution2()).findKthLargest(arr, 2));
    }
}
