public class LinearSearch {

    private LinearSearch() {}
    public static <E> int search(E[] data, E target) {

        // loop invariant
        for (int i=0; i < data.length; i++)
            if (data[i].equals(target))
                return i;

        return -1;

    }

    public static void main(String[] args) {

        int[] dataSize = {1000000, 10000000};
        for (int n: dataSize) {
            Integer[] data = ArrayGenerator.generateOrderedArray(n);

            // Test the cost time
            long startTime = System.nanoTime();
            // Generic can only accept class type not basic type
            for(int k = 0; k < 100; k++)
                LinearSearch.search(data, n);
            long endTime = System.nanoTime();
            double durationTime = (endTime - startTime) / 1000000000.0;
            System.out.println("n = " + n + ", 100 runs: " + durationTime + " s");
        }


        /*
        int res2 = LinearSearch.search(data, 666);
        System.out.println(res2);

        Student[] students = {new Student("Alice"),
                              new Student("Bobo"),
                              new Student("Tony")};

        Student tony = new Student("tony");
        int res3 = LinearSearch.search(students, tony);
        System.out.println(res3);
         */
    }
}
