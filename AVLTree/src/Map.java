public interface Map <K, V> {

    void add(K key, V value);

    boolean isEmpty();

    V get(K key);

    int getSize();

    boolean contains(K key);

    void set(K key, V newValue);

    V remove(K key);
}
