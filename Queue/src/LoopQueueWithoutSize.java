public class LoopQueueWithoutSize <E> implements Queue <E>{

    private E[] data;
    private int front, tail;

    public LoopQueueWithoutSize(int capacity) {
        data = (E[]) new Object[capacity + 1];
        front = 0;
        tail = 0;
    }

    public LoopQueueWithoutSize() {
        this(10);
    }

    // if tail >= front, size = tail - front
    // if tail < front, size = data.length - (front - tail)
    // 此時, 沒有元素的部分為 front - tail ; 整體元素個數為 data.length - (front - tail)
    @Override
    public int getSize() {
        return tail >= front? tail - front: data.length - (front - tail);
    }

    public int getCapacity() {
        return data.length - 1;
    }

    @Override
    public E getFront() {
        if (isEmpty())
            throw new IllegalArgumentException("Queue is empty.");
        return data[front];
    }

    @Override
    public boolean isEmpty() {
        return tail == front;
    }

    @Override
    public void enqueue(E e) {

        if ((tail + 1) % data.length == front)
            resize(getCapacity() * 2);

        data[tail] = e;
        tail = (tail + 1) % data.length;
    }

    @Override
    public E dequeue() {

        if (isEmpty())
            throw new IllegalArgumentException("Queue is empty.");

        E ret = data[tail];
        front = (front + 1) % data.length;

        if (getSize() == getCapacity() / 4 && getCapacity() / 2 != 0)
            resize(getCapacity() / 2);

        data[front] = null;
        return ret;
    }

    private void resize(int newCapacity) {

        E[] newData = (E[]) new Object[newCapacity];

        int sz = getSize();
        for (int i = front; i != tail; i++) {
            newData[i] = data[(front + i) % data.length];
        }

        data = newData;
        front = 0;
        tail = sz;
    }

    @Override
    public String toString() {

        StringBuilder res = new StringBuilder();
        res.append(String.format("Queue: size = %d, capacity = %d", getSize(), getCapacity()));

        for (int i = front; i != tail; i++) {
            res.append(data[(front + i) % data.length]);

            if (i != getSize() - 1)
                res.append(", ");
        }
        res.append("]");
        return res.toString();
    }

    public static void main(String[] args){

        LoopQueue<Integer> queue = new LoopQueue<>();
        for(int i = 0 ; i < 10 ; i ++){
            queue.enqueue(i);
            System.out.println(queue);

            if(i % 3 == 2){
                queue.dequeue();
                System.out.println(queue);
            }
        }
    }
}
