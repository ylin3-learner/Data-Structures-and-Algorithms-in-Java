import java.io.IOException;
import java.util.ArrayList;

public class Main {

    // 遞歸計算 1 到 n 的總和
    public static int sum(int n) {
        if (n <= 1) {
            return n;
        }
        return n + sum(n - 1); // 使用遞歸，將當前的 n 加上 (n-1) 的總和
    }

    private static double testMap(Map<String, Integer> map, String filename) throws IOException {

        long startTime = System.nanoTime();

        System.out.println(filename);
        ArrayList<String> words = new ArrayList<>();

        if (FileOperation.readFile(filename, words)) {

//            Collections.sort(words);

            System.out.println("Total Words: " + words.size());

            for (String word: words) {
                if (map.contains(word)) {
                    map.set(word, map.get(word) + 1);

                } else
                    map.add(word, 1);
            }

            System.out.println("Total different words: " + map.getSize());
            System.out.println("Frequency of Pride: " + map.get("pride"));
            System.out.println("Frequency of Pride: " + map.get("prejudice"));
        }

        long endTime = System.nanoTime();

        return (endTime - startTime) / 1000000000.0;
    }

    public static void main(String[] args) throws IOException {

        String filename = "pride-and-prejudice.txt";

        AVLTree<String, Integer> avlTree = new AVLTree<>();
        BST<String, Integer> bst = new BST<>();
        RBTree<String, Integer> rbTree = new RBTree<>();

        System.out.println("AVL Tree: " + testMap(avlTree, filename) + " s");
        System.out.println();
        System.out.println("BST: " + testMap(bst, filename) + " s");
        System.out.println();
        System.out.println("RedBlackTree: " + testMap(rbTree, filename) + " s");
    }
}
