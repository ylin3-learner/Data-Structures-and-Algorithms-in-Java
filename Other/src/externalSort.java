import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;

public class externalSort {

    private static int B = 4;  // each block's size : block (unit between I/O & CPU)
    private static int M = 40; // Assumed CPU's memory
    private static int K = 5000;

    private externalSort() {}

    // (N/B) * log2^(N/B)
    public static void externalMergeSort(String filename, String outfile) throws IOException {
        int N = countLinesInFile(filename);

        // Ensure the output file is created or cleared
        Files.deleteIfExists(Paths.get(outfile));
        Files.createFile(Paths.get(outfile));

        double startTime = System.nanoTime();
        for (int i = 0; i < N / B; i++)
            sortOneBlock(filename, i);

        for (int bsz = 1; bsz < N / B; bsz += bsz) {
            for (int i = 0; i + bsz < N / B; i += bsz) {
                merge(filename, i, i + bsz - 1, Math.min(i + bsz + bsz - 1, N / B - 1), outfile);
            }
        }
        double endTime = System.nanoTime();
        System.out.println("Cost time: " + (endTime - startTime) / 100000000.0);
    }

    // (N/B) * log2^(N/M) - 優化 1 : B --> M
    public static void externalMergeSort2(String filename, String outfile) throws IOException {
        int N = countLinesInFile(filename);

        // Ensure the output file is created or cleared
        Files.deleteIfExists(Paths.get(outfile));
        Files.createFile(Paths.get(outfile));

        double startTime = System.nanoTime();
        for (int i = 0; i < N / M; i++)
            sortOneBlock(filename, i);

        for (int msz = 1; msz < N / M; msz *= K) {
            for (int i = 0; i + msz < N / M; i += K * msz) {
                mergeK(filename, i, Math.min(i += K * msz - 1, N / B - 1), K, outfile);
            }
        }
        double endTime = System.nanoTime();
        System.out.println("Cost time: " + (endTime - startTime) / 100000000.0);
    }

    // 優化 2 [merge_k 對 k 個列表進行歸併]: log2(N/M) --> logk(N/M)

    // Class to represent a node in the priority queue
    private static class BlockNode {
        String value;
        int blockIndex;
        int index;
        String[] block;

        BlockNode(String value, int blockIndex, int index, String[] block) {
            this.value = value;
            this.blockIndex = blockIndex;
            this.index = index;
            this.block = block;
        }
    }

    private static void mergeK(String filename, int start, int end, int k, String outfile) throws IOException {
        // Priority queue to access the smallest elements
        PriorityQueue<BlockNode> pq = new PriorityQueue<>(
                (node1, node2) -> node1.value.compareTo(node2.value)
        );

        // Reading each block and inserting the first element into the priority queue
        for (int i = start; i <= end; i++) {
            String[] block = readBlock(filename, i * B, B);
            if (block != null && block.length > 0) {
                pq.add(new BlockNode(block[0], i, 0, block));
            }
        }

        ArrayList<String> mergedBlock = new ArrayList<>();

        while (!pq.isEmpty()) {
            BlockNode curNode = pq.poll();

            // Add the smallest element to the merged block
            mergedBlock.add(curNode.value);

            // If the current block has more elements, push the next element to the priority queue
            if (curNode.index + 1 < curNode.block.length) {
                pq.add(new BlockNode(curNode.block[curNode.index + 1], curNode.blockIndex, curNode.index + 1, curNode.block));
            }

            // Write to outfile if the merged block reaches size B
            if (mergedBlock.size() == B) {
                write(outfile, mergedBlock, true);
                mergedBlock.clear();
            }
        }

        // Write remaining elements to outfile
        if (!mergedBlock.isEmpty()) {
            write(outfile, mergedBlock, true);
        }
    }

    private static int countLinesInFile(String filename) throws IOException {
        CharsetDecoder decoder = StandardCharsets.UTF_8.newDecoder()
                .onMalformedInput(CodingErrorAction.REPLACE)
                .onUnmappableCharacter(CodingErrorAction.REPLACE);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                Files.newInputStream(Paths.get(filename)), decoder))) {
            int lines = 0;
            while (reader.readLine() != null) {
                lines++;
            }
            return lines;
        }
    }

    private static void sortOneBlock(String filename, int start) throws IOException {
        String[] block = readBlock(filename, start * B, B);
        if (block == null || block.length == 0) return;

        // Use Insertion Sort or other sorting method
        ArrayList<String> blockList = new ArrayList<>(Arrays.asList(block));
//        blockList.removeIf(s -> s == null);

        Collections.sort(blockList);

        // Write sorted blockList to file
        write(filename, blockList, false);
    }

    private static void write(String filename, ArrayList<String> block, boolean append) throws IOException {
        // Open file for writing, either create it or append to it
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filename),
                append ? StandardOpenOption.APPEND : StandardOpenOption.CREATE,
                StandardOpenOption.WRITE)) {
            for (String line : block) {
                writer.write(line);
                writer.newLine();
            }
        }
    }

    private static void merge(String filename, int start, int mid, int end, String outfile) throws IOException {
        String[] Block_IN_0 = readBlock(filename, start * B, B);
        String[] Block_IN_1 = readBlock(filename, (mid + 1) * B, B);

        ArrayList<String> Block_OUT = new ArrayList<>();
        int mpos_0 = 0, mpos_1 = 0;

        while (mpos_0 < Block_IN_0.length && mpos_1 < Block_IN_1.length) {
            if (Block_IN_0[mpos_0].compareTo(Block_IN_1[mpos_1]) <= 0) {
                Block_OUT.add(Block_IN_0[mpos_0++]);
            } else {
                Block_OUT.add(Block_IN_1[mpos_1++]);
            }
            if (Block_OUT.size() == B) {
                write(outfile, Block_OUT, true);
                Block_OUT.clear();
            }
        }

        while (mpos_0 < Block_IN_0.length) {
            Block_OUT.add(Block_IN_0[mpos_0++]);
            if (Block_OUT.size() == B) {
                write(outfile, Block_OUT, true);
                Block_OUT.clear();
            }
        }

        while (mpos_1 < Block_IN_1.length) {
            Block_OUT.add(Block_IN_1[mpos_1++]);
            if (Block_OUT.size() == B) {
                write(outfile, Block_OUT, true);
                Block_OUT.clear();
            }
        }

        if (!Block_OUT.isEmpty()) {
            write(outfile, Block_OUT, true);
        }
    }

    private static String[] readBlock(String filename, int start, int length) throws IOException {
        CharsetDecoder decoder = StandardCharsets.UTF_8.newDecoder()
                .onMalformedInput(CodingErrorAction.REPLACE)
                .onUnmappableCharacter(CodingErrorAction.REPLACE);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                Files.newInputStream(Paths.get(filename)), decoder))) {
            int i = 0;
            for (; i < start; i++) {
                reader.readLine(); // Skip lines
            }

            String[] block = new String[length];
            for (i = 0; i < length; i++) {
                String line = reader.readLine();
                if (line == null) break;
                block[i] = line;
            }
            return Arrays.copyOf(block, i);
        }
    }

    public static void main(String[] args) throws IOException {
        String filename = "test.txt";
        String outfile = "sorted.txt";
        String outfile2 = "sorted2.txt";

        externalMergeSort(filename, outfile);
        externalMergeSort(filename, outfile2);
        System.out.println("External merge sort completed.");
    }
}
