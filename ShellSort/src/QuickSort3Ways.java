import java.util.Random;

public class QuickSort3Ways {

    private QuickSort3Ways() {}

    public static<E extends Comparable<E>> void sort(E[] data) {

        Random rnd = new Random();
        sort(data, 0, data.length - 1, rnd);
    }

    private static<E extends Comparable<E>> void sort(E[] data, int l, int r, Random rnd) {

        if (l >= r) return;

        // data[l+1, lt] < v , data[lt+1, i-1] == v, data[gt, r] > v
        int p = l + rnd.nextInt(r - l + 1);
        swap(data, l, p);

        int lt = l, i = l + 1, gt = r + 1;

        while (i < gt) {

            if (data[i].compareTo(data[l]) < 0) {
                lt ++;
                swap(data, i, lt);
                i ++;
            } else if (data[i].compareTo(data[l]) > 0) {
                gt --;
                swap(data, i, gt);
            } else {
                i ++;
            }
        }

        swap(data, l, lt);
        // After sort: data[l, lt - 1] < v, data[gt, r] > v
        sort(data, l, lt - 1, rnd);
        sort(data, gt, r, rnd);
    }

    private static<E> void swap(E[] data, int i, int j) {

        E temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }
}
