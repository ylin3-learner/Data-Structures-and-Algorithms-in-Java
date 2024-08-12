import java.util.Random;

public class QuickSort2Ways {

    private QuickSort2Ways() {}

    // Top-Down
    public static <E extends Comparable<E>> void sort(E[] data) {

        Random rnd = new Random();
        sort(data, 0, data.length - 1, rnd);
    }

    private static<E extends Comparable<E>> void sort(E[] data, int l, int r, Random rnd) {


        if (l >= r) return;

        // 在這行程式碼中，你試圖生成一個介於 l 和 r 之間的隨機數。然而，如果 l 大於 r，則 r - l + 1 會是負數，這將導致 nextInt() 方法拋出 IllegalArgumentException。
        // 通過確保 l 不大於 r 來解決，這樣就不會生成負數的邊界值。
        int p = partition(data, l, r, rnd);

        sort(data, l, p-1, rnd);
        sort(data, p+1, r, rnd);
    }

    private static<E extends Comparable<E>> int partition(E[] data, int l, int r, Random rnd) {

        int p = l + rnd.nextInt(r - l + 1);
        swap(data, l, p);

        // loop invariant : arr[l, i-1] <= p, arr[i, j] >= p
        int i = l + 1, j = r;

        while (true) {

            while (i <= j && data[i].compareTo(data[l]) < 0)
                i ++;

            while (j >= i && data[j].compareTo(data[l]) > 0)
                j --;

            if (i >= j) break;

            // data[i].compareTo(data[l]) >= 0 || data[j].compareTo(data[l]) <= 0
            swap(data, i, j);
            i ++;
            j --;
        }

        // when two sub-arrays finally meet each other
        swap(data, l, j);
        return j;
    }

    private static <E>void swap(E[] data, int i, int j) {
        E temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }
}
