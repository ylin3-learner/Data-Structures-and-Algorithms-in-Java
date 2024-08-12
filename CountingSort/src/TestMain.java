public class TestMain {

    public static void main(String[] args) {

        int n = 1000000, w = 200;

        String[] arr = ArrayGenerator.generateRandomSameLengthStringArray(n, w);
        String[] arr2 = ArrayGenerator.generateRandomStringArray(n, w);
        Integer[] arr3 = ArrayGenerator.generateRandomArray(n, n);
        SortingHelper.sortTest("LSDSort", arr);
        SortingHelper.sortTest("MSDSort", arr2);
        SortingHelper.sortTest("BucketSort", arr3);
    }
}
