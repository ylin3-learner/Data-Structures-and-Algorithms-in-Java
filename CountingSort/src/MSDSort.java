public class MSDSort {

    private MSDSort() {}

    // Advantage :  1. recursion can be stopped in advance 2. Time Complexity : O(w * n) -- when w << n, it will be very fast compared to O(nlog^n)
    public static void sort(String[] arr) {

        String[] temp = new String[arr.length];
        sort(arr, 0, arr.length - 1, 0, temp);
    }

    // Sort from arr[left, right] based on rth index of String, if rth index is space, put in the first space of cnt means the least character
    // Temp, and arr has the same offset (left)
    private static void sort(String[] arr, int left, int right, int r, String[] temp) {

        if (left >= right) return;

        int R = 256;
        int[] cnt = new int[R + 1];
        int[] index = new int[R + 2];

        // Count sort
        for (int i = left; i <= right; i++)
            cnt[r >= arr[i].length() ? 0 : (arr[i].charAt(r) + 1)] ++;

        for (int i = 0; i < R + 1; i++) {
            index[i + 1] = index[i] + cnt[i];
        }

        for (int i = left; i <= right; i++) {
            int p = r >= arr[i].length() ? 0 : (arr[i].charAt(r) + 1);
            temp[left + index[p]] = arr[i];
            index[p] ++;
        }

        for (int i = left; i <= right; i++)
            arr[i] = temp[i];

        // 遞歸下去
        for (int i = 0; i < R; i++)
            sort(arr, left + index[i], left + index[i + 1] - 1, r + 1, temp);
    }

    public static void main(String[] args) {

        String[] arr = {"BCA", "CBAA", "AC", "BADFE", "ABC", "CBA"};
        MSDSort.sort(arr);
        for (String s: arr)
            System.out.println(s);
    }
}
