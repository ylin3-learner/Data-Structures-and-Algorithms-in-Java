public interface Map <K extends Comparable<K>, V>{

    void add(K key, V value);

    V remove(K key);

    V get(K key);

    boolean contains(K key);

    boolean isEmpty();

    int getSize();

    void set(K key, V newValue);
}
