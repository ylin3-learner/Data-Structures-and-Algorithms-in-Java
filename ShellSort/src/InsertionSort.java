public class InsertionSort {

    private InsertionSort() {}

    public static <E extends Comparable<E>> void sort(E[] data) {

        for (int i = 0; i < data.length; i++) {

            E temp = data[i];
            // 2, 1
            int j;
            for (j = i; j - 1 >= 0 && temp.compareTo(data[j-1]) < 0; j--) {
                data[j] = data[j-1];
            }

            data[j] = temp;
        }
    }

    public static void main(String[] args) {
        int n = 5;

        Integer[] arr = ArrayGenerator.generateRandomArray(n, n);
        SortingHelper.sortTest("InsertionSort", arr);
    }
}
