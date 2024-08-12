import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        ArrayList<String> words = new ArrayList<>();
        if(FileOperation.readFile("pride-and-prejudice.txt", words) &&
                FileOperation.readFile("a-tale-of-two-cities.txt", words)){

            // Test BST
            long startTime = System.nanoTime();

            BSTSet<String> set = new BSTSet<>();
            for(String word: words)
                set.add(word);

            for(String word: words)
                set.contains(word);

            long endTime = System.nanoTime();

            double time = (endTime - startTime) / 1000000000.0;

            System.out.println("Total different words: " + set.getSize());
            System.out.println("BSTSet: " + time + " s");

            // ---

            // Test TreeMap Trie
            startTime = System.nanoTime();

            Trie trie = new Trie();
            for(String word: words)
                trie.add(word);

            for(String word: words)
                trie.contains(word);

            endTime = System.nanoTime();

            time = (endTime - startTime) / 1000000000.0;

            System.out.println("Total different words: " + trie.getSize());
            System.out.println("TreeMap Trie: " + time + " s");

            // ---

            // Test HashMap Trie
            startTime = System.nanoTime();

            Trie2 trie2 = new Trie2();
            for(String word: words)
                trie2.add(word);

            for(String word: words)
                trie2.contains(word);

            endTime = System.nanoTime();

            time = (endTime - startTime) / 1000000000.0;

            System.out.println("Total different words: " + trie.getSize());
            System.out.println("HashMap Trie: " + time + " s");

            // ---

            // Test Array(Map) Trie
            startTime = System.nanoTime();

            Trie3 trie3 = new Trie3();
            for(String word: words)
                trie3.add(word);

            for(String word: words)
                trie3.contains(word);

            endTime = System.nanoTime();

            time = (endTime - startTime) / 1000000000.0;

            System.out.println("Total different words: " + trie.getSize());
            System.out.println("Array(Map) Trie: " + time + " s");
        }
    }
}

/**
 * ，基于 TreeMap 的 Trie 是最慢的。
 *
 * 基于数组的 Trie最快，因为相比 HashMap，数组也不需要计算哈希函数
 * 对于 Trie 来说，其实还有一个重要的时间开销。由于 Trie 消耗的空间比较大（每个节点会有若干个指针），
 * 所以给这些承载指针的空间（TreeMap，HashMap 或者数组）开辟内存也是一个额外的时间消耗
 */