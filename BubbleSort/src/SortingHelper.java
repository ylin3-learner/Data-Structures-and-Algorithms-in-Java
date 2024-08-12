public class SortingHelper {

    private SortingHelper() {}

    private static<E extends Comparable<E>> boolean isSorted(E[] arr) {

        for (int i = 1; i < arr.length; i++) {
            if (arr[i+1].compareTo(arr[i]) < 0)
                return false;
        }

        return true;
    }

    public static<E extends Comparable<E>> void sortTest(String sortName, E[] arr) {

        long startTime = System.nanoTime();

        if (sortName.equals("BubbleSort")) {
            BubbleSort.sort(arr);
        } else if (sortName.equals("BubbleSort2")) {
            BubbleSort.sort2(arr);
        } else if (sortName.equals("BubbleSort3")) {
            BubbleSort.sort3(arr);
        } else if (sortName.equals("BubbleSort4")) {
            BubbleSort.sort4(arr);
        }

        long endTime = System.nanoTime();

        double durationTime = (endTime - startTime) / 1000000000.0;

        System.out.printf("%s, n = %d : %f s%n", sortName, arr.length, durationTime);
    }
}
