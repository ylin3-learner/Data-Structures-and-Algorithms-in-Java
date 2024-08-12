import java.util.Arrays;

public class Array<E> {

    private E[] data;
    private int size;

    // Constructor method: Give the array's length, capacity, to construct array
    public Array(int capacity) {
        data = (E[]) new Object[capacity];
        size = 0;
    }

    // constructor without parameters with its default length: 10
    public Array() {
        this(10);
    }

    // the current numbers of elements in array
    public int getSize() {
        return size;
    }

    // the whole length of array
    public int getCapacity() {
        return data.length;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    /*
        Add Elements in array
     */
    public void addFirst(E e) {
        add(0, e);
    }

    // Add a new element behind whole elements
    public void addLast(E e) {

//        if (size == data.length)
//            throw new IllegalArgumentException("AddLast failed. Array is full.");
//
//        data[size] = e;
//        size ++;
        add(size, e);
    }

    // Add a new element, e, in the index position
    public void add(int index, E e) {

        if (index < 0 || index > size)
            throw new IllegalArgumentException("Add failed. Required index >= 0 and index <= size");

        if (size == data.length)
            resize(2 * data.length);

        for (int i = size - 1; i >= index; i--)
            data[i+1] = data[i];

        data[index] = e;
        size ++;
    }

    // Search if array contains certain element
    public boolean contains(E e) {

        for (int i = 0; i < size; i++) {
            if (data[i] == e)
                return true;
        }

        return false;
    }


    // Search the index that element locates , if not exists return -1
    public int find(E e) {

        for (int i = 0; i < size; i++) {
            if (data[i].equals(e))
                return i;
        }

        return -1;
    }

    // remove the element at index position and return the deleted element
    public E remove(int index) {

        if (index < 0 || index > size)
            throw new IllegalArgumentException("Remove failed. Index is illegal.");

        E ret = data[index];

        for (int i = index + 1; i < size; i++)
            data[i - 1] = data[i];
        size --;
        data[size] = null;  // loitering objects != memory leak

        if (size == data.length / 4 && data.length / 2 != 0)
            resize(data.length / 2);
        return ret;
    }

    // Delete the first element
    public E removeFirst() {
        return remove(0);
    }

    public E removeLast() {
        return remove(size - 1);
    }

    // remove e from array
    public void removeElement(E e) {
        int index = find(e);
        if (index != -1)
            remove(index);
    }

    // Prevent illegal indexing search by hiding data
    public E get(int index) {

        if (index < 0 || index > size)
            throw new IllegalArgumentException("Get failed. Index is illegal.");
        return data[index];
    }

    public E getFirst() {
        return get(0);
    }

    public E getLast() {
        return get(size - 1);
    }

    // Modify the element at index position as e
    public void set(int index, E e) {

        if (index < 0 || index > size)
            throw new IllegalArgumentException("Set failed. Index is illegal.");
        data[index] = e;
    }

    @Override
    public String toString() {

        StringBuilder res = new StringBuilder();
        res.append(String.format("Array: size = %d, capacity = %d\n", size, data.length));

        res.append('[');
        for (int i = 0; i < size; i++) {
            res.append(data[i]);
            if (i != size - 1)
                res.append(", ");
        }

        res.append(']');
        return res.toString();
    }

    private void resize(int newCapacity) {

        E[] newData = (E[]) new Object[newCapacity];
        for (int i = 0; i < size; i++)
            newData[i] = data[i];
        data = newData;
    }
}
