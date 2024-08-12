import java.util.Random;

public class ArrayGenerator {

    private ArrayGenerator() {}

    public static Integer[] generateOrderedArray(int n) {
        Integer[] arr = new Integer[n];

        for (int i = 0; i < n; i++)
            arr[i] = i;
        return arr;
    }

    public static Integer[] generateRandomArray(int n, int bound) {

        Integer[] arr = new Integer[n];

        Random random = new Random();
        for (int i = 0; i < n; i++) {
            arr[i] = random.nextInt(bound);
        }

        return arr;
    }

    public static String[] generateRandomSameLengthStringArray(int n, int w) {

        String[] arr = new String[n];
        Random rnd = new Random();

        // 隨機生成的字符沒有每個都可以打印
        // 33 - 126 可打印 (range: 126 - 33 + 1 = 94)
        for (int i = 0; i < n; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < w; j++)
                sb.append((char) (rnd.nextInt(94) + 33));
            arr[i] = sb.toString();
        }

        // Debugging: Check if all strings have the same length
        for (String s : arr) {
            if (s.length() != w) {
                System.out.println("String with incorrect length found: " + s + " Length: " + s.length());
            }
        }

        return arr;
    }

    // n : numbers of elements in arr, bound : max length of each element in arr
    public static String[] generateRandomStringArray(int n, int bound) {

        String[] arr = new String[n];
        Random rnd = new Random();

        for (int i = 0; i < n; i++) {
            StringBuilder stringBuilder = new StringBuilder();

            int w = rnd.nextInt(bound);
            for (int j = 0; j < w; j++) {
                stringBuilder.append((char)(rnd.nextInt(94) + 33));
            }
            arr[i] = stringBuilder.toString();
        }

        return arr;
    }
}
