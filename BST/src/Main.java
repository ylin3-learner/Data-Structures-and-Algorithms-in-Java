import java.util.ArrayList;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        BST<Integer> bst = new BST<>();

        int[] nums = {5, 4, 7, 3, 2, 6};
        for (int num: nums)
            bst.addNR(num);

        bst.preOrder();
        System.out.println();

        System.out.println("PreOrder with Stack: ");
        bst.preOrderNR();
        System.out.println();

        System.out.println("PreOrder with Queue: ");
        bst.preOrderWithQueue();
        System.out.println();

        bst.levelOrder();
//        bst.inOrder();
//        System.out.println();
//
//        bst.postOrder();
//        System.out.println();
//        System.out.println(bst);

        // Use Random to add element in bst
        // Use removeMin to add element in arr. And test arr is sorted or not by removeMin.

        System.out.println(bst);

        int n = 1000;
        Random rnd = new Random();

        for (int i = 0; i < n; i++) {
            bst.add(rnd.nextInt(10000));
        }

        ArrayList<Integer> arr = new ArrayList<>();
        while (!bst.isEmpty())
            arr.add(bst.removeMin());

        System.out.println(arr);

        // Test removeMin
        for (int i = 1; i < arr.size(); i++) {
            if (arr.get(i - 1) > arr.get(i))
                throw new IllegalArgumentException("Error");
        }
        System.out.println("removeMin Test Completed!");
    }
}
