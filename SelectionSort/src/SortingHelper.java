public class SortingHelper {

    private SortingHelper() {}

    public static <E extends Comparable<E>> boolean isSorted(E[] arr) {

        for (int i = 1; i < arr.length; i++)
            if (arr[i-1].compareTo(arr[i]) > 0)
                return false;
        return true;
    }

    public static <E extends Comparable<E>> void sortTest(String sortName, E[] arr) {

        long startTime = System.nanoTime();
        if (sortName.equals("SelectionSort"))
            SelectionSort.sort(arr);
        else if (sortName.equals("InsertionSort"))
            InsertionSort.sort(arr);
        else if (sortName.equals("InsertionSort2"))
            InsertionSort.sort2(arr);
        else if (sortName.equals("InsertionSort3"))
            InsertionSort.sort3(arr);

        long endTime = System.nanoTime();

        double durationTime = (endTime - startTime) / 1000000000.0;

        if (!SortingHelper.isSorted(arr))
            throw new RuntimeException("SelectionSort failed");
        System.out.printf("%s , n = %d : %f s%n", sortName, arr.length, durationTime);
    }
}
