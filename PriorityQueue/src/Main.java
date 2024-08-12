import java.util.Random;

public class Main {

    public static double testHeap(Integer[] testData, boolean isHeapify) {

        long startTime = System.nanoTime();

        MaxHeap<Integer> maxHeap;
        if (isHeapify) {
            maxHeap = new MaxHeap<>(testData);
        }else {
            maxHeap = new MaxHeap<>();
            for (int num: testData)
                maxHeap.add(num);
        }

        long endTime = System.nanoTime();

        // Test if data is organized
        int[] arr = new int[testData.length];
        for (int i = 0; i < testData.length; i++)
            arr[i] = maxHeap.extractMax();

        for (int i = 1; i < testData.length; i++) {
            if (arr[i-1] < arr[i])
                throw new IllegalArgumentException("Error");
        }

        System.out.println("MaxHeap Test Completed.");

        return (endTime - startTime) / 1000000000.0;
    }

    public static void main(String[] args) {

        int n = 100000;
        Integer[] testData = new Integer[n];

        Random random = new Random();
        for (int i = 0; i < n; i++)
            testData[i] = random.nextInt(Integer.MAX_VALUE);

        double time1 = testHeap(testData, false);
        System.out.println("Without Heapify: " + time1 + " s");

        double time2 = testHeap(testData, true);
        System.out.println("Heapify: " + time2 + " s");

    }
}
