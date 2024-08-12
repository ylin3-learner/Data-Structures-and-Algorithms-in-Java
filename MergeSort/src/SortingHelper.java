public class SortingHelper {

    private SortingHelper() {}

    public static <E extends Comparable<E>> boolean isSorted(E[] arr) {

        for (int i = 1; i < arr.length; i++)
            if (arr[i-1].compareTo(arr[i]) > 0)
                return false;
        return true;
    }

    public static <E extends Comparable<E>> void sortTest(String sortName, E[] arr) {

        // Calculate duration Time
        long startTime = System.nanoTime();

        // Call different sort method
        if (sortName.equals("SelectionSort"))
            SelectionSort.sort(arr);
        else if (sortName.equals("InsertionSort"))
            InsertionSort.sort(arr);
        else if (sortName.equals("MergeSort"))
            MergeSort.sort(arr);
        else if (sortName.equals("MergeSortBU"))
            MergeSort.sortBU(arr);


        long endTime = System.nanoTime();

        double durationTime = (endTime - startTime) / 1000000000.0;

        // check for isSorted()
        if (!SortingHelper.isSorted(arr))
            throw new RuntimeException(sortName + " failed");

        System.out.printf("%s, n = %d : %f s%n", sortName, arr.length, durationTime);
    }
}
