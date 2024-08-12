public class Deque <E> {

    private E[] data;
    private int front, tail;
    private int size;

    public Deque(int capacity) {
        data = (E[]) new Object[capacity];
        front = 0;
        tail = 0;
        size = 0;
    }

    public Deque() {
        this(10);
    }

    public int getCapacity() {
        return data.length - 1;
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void addFront(E e) {

        if (size == getCapacity())
            resize(getCapacity() * 2);

        // make sure the index of new element
        front = front == 0 ? data.length - 1: front - 1;
        data[front] = e;
    }

    // Same logic as enqueue()
    public void addLast(E e) {

        if (size == getCapacity())
            resize(getCapacity() * 2);
        data[tail] = e;

        tail = (tail + 1) % data.length;
        size ++;
    }

    public E removeFront() {

        if (isEmpty())
            throw new IllegalArgumentException("Deque is empty.");

        E ret = data[front];
        front = (front + 1) % data.length;

        if (getSize() == getCapacity() / 4 && getCapacity() / 2 != 0)
            resize(getCapacity() / 2);
        data[front] = null;
        size --;
        return ret;
    }

    public E removeLast() {

        if (isEmpty())
            throw new IllegalArgumentException("Deque is empty.");

        // make sure the index of removed element
        tail = tail == 0 ? data.length - 1: tail - 1;
        E ret = data[tail];
        data[tail] = null;
        size --;

        if (getSize() == getCapacity() / 4 && getCapacity() / 2 != 0)
            resize(getCapacity() / 2);
        return ret;
    }

    public E getFront() {

        if (isEmpty())
            throw new IllegalArgumentException("Deque is empty.");

        return data[front];
    }

    // 因为是双端队列，我们也有一个 getLast 的方法，来获取队尾元素的值
    public E getLast(){
        if(isEmpty())
            throw new IllegalArgumentException("Queue is empty.");

        // 因为 tail 指向的是队尾元素的下一个位置，我们需要计算一下真正队尾元素的索引
        int index = tail == 0 ? data.length - 1 : tail - 1;
        return data[index];
    }

    private void resize(int newCapacity) {

        E[] newData = (E[])new Object[newCapacity];
        for(int i = 0 ; i < size ; i ++)
            newData[i] = data[(i + front) % data.length];

        data = newData;
        front = 0;
        tail = size;
    }

    @Override
    public String toString() {

        StringBuilder res = new StringBuilder();
        res.append(String.format("Deque: size=%d, capacity=%d", getSize(), getCapacity()));
        res.append(" front [");

        for (int i = 0; i < size; i++) {
            res.append(data[i]);

            if (i != size - 1)
                res.append(", ");
        }
        res.append("] tail");
        return res.toString();
    }

    public static void main(String[] args){

        // 在下面的双端队列的测试中，偶数从队尾加入；奇数从队首加入
        Deque<Integer> dq = new Deque<>();
        for(int i = 0 ; i < 16 ; i ++){
            if(i % 2 == 0) dq.addLast(i);
            else dq.addFront(i);
            System.out.println(dq);
        }

        // 之后，我们依次从队首和队尾轮流删除元素
        System.out.println();
        for(int i = 0; !dq.isEmpty(); i ++){
            if(i % 2 == 0) dq.removeFront();
            else dq.removeLast();
            System.out.println(dq);
        }
    }
}
