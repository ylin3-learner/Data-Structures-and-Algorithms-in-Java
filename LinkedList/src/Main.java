//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        LinkedList<Integer> linkedList = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            linkedList.addFirst(i);
            System.out.println(linkedList);
        }

        linkedList.add(2, 666);
        System.out.println(linkedList);

        int j, count = 0, index = 3;
        for (j = 0; j < index; j++) {
            System.out.print(j + " ");
            count ++;
        }
        System.out.println("\n" + count + " times");
        System.out.println("index=" + index + ", index==count? " + (index==count));
        System.out.println("\nAfter loop index: j = " + j);

        linkedList.removeFirst();
        System.out.println(linkedList);

        linkedList.remove(2);
        System.out.println(linkedList);

        linkedList.removeLast();
        System.out.println(linkedList);


        /*
         * Test array's time cost and linked list's time cost
         */

        // 创建一个动态数组，再创建一个链表
        Array<Integer> array = new Array<>();
        LinkedList<Integer> list = new LinkedList<>();

        // 对于 1000 万规模的数据
        int n = 10000000;
        System.out.println("n = " + n);

        // 计时，看将 1000 万个元素放入数组中，时间是多少
        long startTime = System.nanoTime();
        // 对于数组，我们使用 addLast，每一次操作时间复杂度都是 O(1) 的
        for(int i = 0; i < n; i ++)
            array.addLast(i);
        long endTime = System.nanoTime();
        double time = (endTime - startTime) / 1000000000.0;
        System.out.println("Array : " + time + " s");

        // 计时，看将 1000 万个元素放入链表中，时间是多少
        startTime = System.nanoTime();
        // 对于链表，我们使用 addFirst，每一次操作时间复杂度都是 O(1) 的
        for(int i = 0; i < n; i ++)
            list.addFirst(i);
        endTime = System.nanoTime();
        time = (endTime - startTime) / 1000000000.0;
        System.out.println("LinkedList : " + time + " s");

        // 對於動態數組即使有resize, 還是快於linked list? 因為resize 一次申請一大片空間, 所以觸發resize的機會少, 但new node 一次一個
        // resize 的賦值操作將快於 new node() 的內存操作

    }
}