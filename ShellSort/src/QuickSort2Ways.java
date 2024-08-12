import java.util.Random;

public class QuickSort2Ways {

    private QuickSort2Ways() {}

    public static <E extends Comparable<E>> void sort(E[] data) {

        Random rnd = new Random();
        sort(data, 0, data.length - 1, rnd);
    }

    private static <E extends Comparable<E>> void sort(E[] data, int l, int r, Random rnd) {

        if (l >= r) return;

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

        swap(data, l, j);
        return j;
    }

    private static<E> void swap(E[] data, int i, int j) {

        E temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }
}
