public class SortingHelper {

    private SortingHelper() {
    }

    public static <E extends Comparable<E>> boolean isSorted(E[] data) {
        for (int i = 0; i < data.length - 1; i++) {
            if (data[i].compareTo(data[i + 1]) > 0)
                return false;
        }
        return true;
    }

    public static <E extends Comparable<E>> void sortTest(String sortName, E[] arr) {
        long startTime = System.nanoTime();

        if (sortName.equals("LSDSort")) {
            // LSDSort can only accept String[] arr -> cast arr
            String[] strArr = (String[]) arr;
            // However, strArr may be empty
            if (strArr.length == 0) throw new IllegalArgumentException("arr can not be empty.");
            LSDSort.sort(strArr, strArr[0].length());
        } else if (sortName.equals("MSDSort")) {
            String[] strArr = (String[]) arr;
            MSDSort.sort(strArr);
        } else if (sortName.equals("BucketSort")) {
            Integer[] intArr = (Integer[]) arr;
            BucketSort.sort(intArr, 200);
        } else if (sortName.equals("BucketSort2")) {
            Integer[] intArr = (Integer[]) arr;
            BucketSort.sort2(intArr, 100);
        }

        long endTime = System.nanoTime();
        double durationTime = (endTime - startTime) / 1000000000.0;

        // check if data is sorted
        if (!isSorted(arr))
            throw new RuntimeException(sortName + " failed.");

        System.out.printf("%s, n = %d : %f s%n", sortName, arr.length, durationTime);
    }
}
