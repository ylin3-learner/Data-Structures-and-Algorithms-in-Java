public class SortingHelper {

    private SortingHelper() {}

    public static <E extends Comparable<E>> boolean isSorted(E[] arr) {

        // how to compare: similar to insertion sort
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i].compareTo(arr[i + 1]) > 0)
                return false;
        }
        return true;
    }

    public static <E extends Comparable<E>> void sortTest(String sortName, E[] arr) {

        long startTime = System.nanoTime();

        if (sortName.equals("MergeSort"))
            MergeSort.sort(arr);
        else if (sortName.equals("QuickSort"))
            QuickSort.sort(arr);
        else if (sortName.equals("InsertionSort")) {
            InsertionSort.sort(arr);
        } else if (sortName.equals("QuickSort2Ways")) {
            QuickSort2Ways.sort(arr);
        } else if (sortName.equals("QuickSort3Ways")) {
            QuickSort3Ways.sort(arr);
        }

        long endTime = System.nanoTime();

        double durationTime = (endTime - startTime) / 1000000000.0;

        if (!SortingHelper.isSorted(arr))
            throw new RuntimeException(sortName + " failed.");

        System.out.printf("%s, n = %d : %f s%n", sortName, arr.length, durationTime);


    }
}
