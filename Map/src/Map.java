public interface Map <K, V>{

    void add(K key, V value);

    V remove(K key);

    void set(K key, V newValue);

    V get(K key);

    int getSize();

    boolean isEmpty();

    boolean contains(K key);
}
