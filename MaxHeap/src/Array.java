/** Array : 動態數組
 *
 * @param <E> data, int size, int capacity
 *      Array doesn't support Comparable, it can be random ordered.
 */
public class Array <E>{

    private E[] data;
    private int size;

    public Array(int capacity) {
        data = (E[]) new Object[capacity];
        size = 0;
    }

    // default size
    public Array() {
        this(10);
    }

    // Accept E[] arr
    public Array(E[] arr) {

        data = (E[]) new Object[arr.length];
        for (int i = 0; i < arr.length; i++)
            data[i] = arr[i];
        size = arr.length;
    }

    // the whole length of array
    public int getCapacity() {
        return data.length;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    // the current numbers of elements in array
    public int getSize() {
        return size;
    }

    public void add(E e, int index) {

        if (index < 0 || index > data.length)
            throw new RuntimeException("Add failed. Required index >= 0 and <= size");

        if (index == data.length)
            resize(2 * data.length);

        for (int i = size - 1; i >= index; i--) {
            data[i+1] = data[i];
        }

        data[index] = e;
        size ++;
    }

    public void addLast(E e) {
        add(e, size);
    }

    public void addFirst(E e) {
        add(e, 0);
    }

    public E remove(int index) {

        if (index < 0 || index > size)
            throw new RuntimeException("Remove failed. Index is illegal.");

        E ret = data[index];
        for (int i = index + 1; i < size; i++)
            data[i-1] = data[i];

        size --;
        data[size] = null;  // loitering objects

        if (size == data.length / 4 && data.length / 2 != 0)
            resize(data.length / 2);

        return ret;
    }

    public E removeLast() {
        return remove(size - 1);
    }

    public E removeFirst() {
        return remove(0);
    }

    public E get(int index) {

        if (index < 0 || index > data.length)
            throw new RuntimeException("Get failed. Index is illegal.");

        return data[index];
    }

    // Return the index of element
    public int find(E e) {

        for (int i = 0; i < size; i++) {
            if (data[i] == e)
                return i;
        }

        return -1;
    }

    public void set(E e, int index) {

        if (index < 0 || index > size)
            throw new RuntimeException("Set failed. Index is illegal.");

        data[index] = e;
    }

    private void resize(int capacity) {

        E[] newData = (E[]) new Object[capacity];

        for (int i = 0; i < size; i++) {
            newData[i] = data[i];
        }
        // 如果没有 data = newData; 这一行，
        // 即使在函数内部将数据复制到了 newData 中
        // 但是在函数外部，data 仍然指向原始的数组，没有指向新的数组。
        // 这将导致函数外部的代码仍然在操作旧的数组，而不是调整后的数组
        data = newData;
    }

    public boolean contains(E e) {

        for (int i = 0; i < size; i++) {
            if (data[i] == e)
                return true;
        }

        return false;
    }

    public void swap(int i, int j) {

        if (i < 0 || i >= size || j < 0 || j >= size)
            throw new IllegalArgumentException("Swap failed. Index is illegal.");

        E temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

    @Override
    public String toString() {

        StringBuilder res = new StringBuilder();
        res.append(String.format("Array: size = %d, capacity = %d\n", size, data.length));

        res.append("[");
        for (int i = 0; i < size; i++) {
            res.append(data[i]);
            if (i != size - 1)
                res.append(", ");
        }

        return res.toString();
    }
}
