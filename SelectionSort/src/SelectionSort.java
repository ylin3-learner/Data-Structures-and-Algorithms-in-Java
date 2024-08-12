public class SelectionSort {

    private SelectionSort() {}
    // 选择排序的核心思想是在每次迭代中找到未排序部分的最小元素，然后将其与未排序部分的第一个元素交换位置。
    // 我们只需比较未排序部分的元素，而无需比较已排序部分的元素。
    public static <E extends Comparable<E>> void sort(E[] arr) {

        // arr[0..i) -ordered elements ; arr[i..n) -unordered elements
        /**
         * i < arr.length 循环会一直迭代到数组的最后一个元素
         *
         * i < arr.length - 1 循环将在数组的倒数第二个元素处停止。
         * 不包括最后一个元素。这可以避免在最后一次迭代中与自身进行比较
         */


        for (int i = 0; i < arr.length - 1; i++) {
            // choose the min value in [i...n)
            int minIndex = i;
            // 使用 i + 1 作为内部循环的起始点可以有效减少不必要的比较
            // 已经将前面的元素排好序，不再需要与它们进行比较。
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j].compareTo(arr[minIndex]) < 0) {
                    minIndex = j;
                }
            }

            // 添加检查以避免不必要的交换
            // 如果最小元素的索引与外部循环变量 i 相同，说明最小元素已经在正确的位置，无需进行交换。
            if (minIndex != i) // Added a check to avoid unnecessary swaps
                swap(arr, i, minIndex);
        }
    }

    public static <E extends Comparable<E>> void sort2(E[] arr) {
        // choose maxValue from [0..n)
        for (int i = arr.length - 1; i >= 0; i--) {
            int maxIndex = i;
            for (int j = i; j >= 0; j--) {
                if (arr[j].compareTo(arr[maxIndex]) > 0)
                    maxIndex = j;
            }

            swap(arr, i, maxIndex);
        }
    }

    private static <E> void swap(E[] arr, int i, int j) {
        E temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        Integer[] data = {6, 4, 2, 3, 1, 5};
//        SelectionSort.sort(data);
        SortingHelper.sortTest("SelectionSort", data);
        for (int e: data) {
            System.out.print(e+" ");
        }

        System.out.println();
        Student[] students = {new Student("Alice", 98),
                              new Student("BoBo", 96),
                              new Student("Tony", 91)};
        SelectionSort.sort(students);
        for (Student student: students) {
            System.out.print(student+", ");
        }

        System.out.println();

        int[] dataSets = {10000, 100000};
        for (int e: dataSets) {
            Integer[] arr = ArrayGenerator.generateRandomArray(e, e);
            SortingHelper.sortTest("SelectionSort", arr);
        }

        SelectionSort.sort2(data);
        for (int e: data) {
            System.out.print(e+" ");
        }
    }
}

