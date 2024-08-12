import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class externalBinarySearch {

    private static int B = 4;

    private externalBinarySearch() {}

    public static<E extends Comparable<E>> int externalSearch(String filename, String target) throws IOException {
        // Check if file exists and is readable
        if (!Files.exists(Paths.get(filename)) || !Files.isReadable(Paths.get(filename))) {
            System.out.println("Error: The file does not exist or cannot be read.");
            return -1; // Return -1 to indicate an error
        }

        // Calculate N as the total number of lines in the file
        int N = countLinesInFile(filename);

        int l = 0, r = N / B - 1;

        // Iterate each block - if mid > block[i] r = mid - 1; else: l = mid + 1;
        while (l <= r) {

            int mid = l + (r - l) / 2;
            String[] Block = readBlock(filename, mid * B, B);

            if (Block == null || Block[0] == null) {
                System.out.println("Error: Could not read block properly.");
                return -1;
            }

            if (Block[0].compareTo(target) <= 0 &&  target.compareTo(Block[B-1]) <= 0) {
                // Perform an inner search within this block
                int index = innerSearch(Block, target);
                return index == -1 ? -1 : mid * B + index;  // Return the global index
            }

            if (Block[Block.length - 1].compareTo(target) < 0) {
                l = mid + 1;
            } else {
                r = mid - 1;  // target > Block[0]
            }
        }
        return -1;
    }

    private static<E extends Comparable<E>> int innerSearch(String[] Block, String target) {

        int l = 0, r = Block.length - 1;

        // binary Search
        while (l <= r) {

            int mid = l + (r - l) / 2;
            if (Block[mid].equals(target)) return mid;

            if (Block[mid].compareTo(target) < 0)
                l = mid + 1;
            else
                r = mid - 1; // Block[mid] > target
        }

        return -1;
    }

    //  // Read a block of B strings starting at a given position in the file
    private static String[] readBlock(String filename, int start, int length) throws IOException {

        if (filename == null || filename.isEmpty())
            throw new IllegalArgumentException("File cannot be empty");

        try(BufferedReader reader = Files.newBufferedReader(Paths.get(filename))) {
            reader.skip((long) start * length);

            // read data from files
            String[] block = new String[length];
            for (int i = 0; i < length; i++) {
                block[i] = reader.readLine();
                if (block[i] == null) break; // end of file
            }
            return block;
        }
    }

    private static int countLinesInFile(String filename) throws IOException {
        BufferedReader reader = Files.newBufferedReader(Paths.get(filename));

        int lines = 0;
        while (reader.readLine() != null)
            lines ++;
        return lines;
    }

    public static void main(String[] args) throws IOException {

        // Example usage:
        String filename = "test.txt";
        String target = "date";

        int index = externalSearch(filename, target);
        if (index != -1) {
            System.out.println("Found at index: " + index);
        } else {
            System.out.println("Not found");
        }
    }
}
