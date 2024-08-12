//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static double testStack(Stack<Integer> stack, int opCount) {

        long startTime = System.nanoTime();

        for (int i = 0; i < opCount; i++) {
            stack.push(i);
        }

        for (int i = 0; i < opCount; i++) {
            stack.pop();
        }

        long endTime = System.nanoTime();

        return (endTime - startTime) / 1000000000.0;
    }

    public static void main(String[] args) {

        int opCount = 100000;

        ArrayStack<Integer> arrayStack = new ArrayStack<>();
        LinkedListStack<Integer> linkedListStack = new LinkedListStack<>();

        double time1 = testStack(arrayStack, opCount);
        System.out.println("ArrayStack: time: " + time1 + " s");

        double time2 = testStack(linkedListStack, opCount);
        System.out.println("LinkedListStack: time: " + time2 + " s");

// This time comparison is complicated, stack implemented from linked list may cost more for time cost of new Node
    }
}