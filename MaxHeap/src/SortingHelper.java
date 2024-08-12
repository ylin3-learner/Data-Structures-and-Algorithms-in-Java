public class SortingHelper {

    private SortingHelper() {}

    public static<E extends Comparable<E>> boolean isSorted(E[] data) {
        for (int i = 0; i < data.length - 1; i++) {
            if (data[i].compareTo(data[i+1]) > 0)
                return false;
        }
        return true;
    }

    public static<E extends Comparable<E>> void sortTest(String sortName, E[] data) {
        long startTime = System.nanoTime();
        if (sortName.equals("HeapSort"))
            HeapSort.sort(data);
        else if (sortName.equals("QuickSort2Ways")) {
            QuickSort2Ways.sort(data);
        } else if (sortName.equals("QuickSort3Ways")) {
            QuickSort3Ways.sort(data);
        } else if (sortName.equals("MergeSort")) {
            MergeSort.sort(data);
        } else if (sortName.equals("HeapSort2")) {
            HeapSort.sort2(data);
        } else {
            throw new IllegalArgumentException("Unsupported sorting algorithm: " + sortName);
        }
        long endTime = System.nanoTime();
        double durationTime = (endTime - startTime) / 1000000000.0;

        // check if data is sorted
        if (!isSorted(data))
            throw new RuntimeException(sortName + " failed.");

        System.out.printf("%s, n = %d : %f s%n", sortName, data.length, durationTime);
    }
}
