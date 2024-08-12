public interface UF {

    // Connectivity Problem

    int getSize();

    // Parameters are the index of elements
    boolean isConnected(int p, int q);
    void unionElements(int p, int q);
}
