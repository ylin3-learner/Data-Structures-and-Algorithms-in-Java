import java.util.Random;

public class Main {

    private static double testUF(UF uf, int m) {

        int size = uf.getSize();
        Random random = new Random();

        long starTime = System.nanoTime();

        for (int i = 0; i < m; i++) {
            int a = random.nextInt(size);
            int b = random.nextInt(size);
            uf.unionElements(a, b);
        }

        for (int i = 0; i < m; i++) {
            int a = random.nextInt(size);
            int b = random.nextInt(size);
            uf.isConnected(a, b);
        }

        long endTime = System.nanoTime();

        return (endTime - starTime) / 1000000000.0;
    }

    public static void main(String[] args) {
        int size = 100000;
        int m = 10000;

        UnionFind1 uf1 = new UnionFind1(size);

        double time1 = testUF(uf1, m);
        System.out.printf("UnionFind1: size: %d, time:%f%n", size, time1);

        UnionFind2 uf2 = new UnionFind2(size);
        double time2 = testUF(uf2, m);
        System.out.printf("UnionFind2: size: %d, time:%f%n", size, time2);

        UnionFind3 uf3 = new UnionFind3(size);
        double time3 = testUF(uf3, m);
        System.out.printf("UnionFind3: size: %d, time:%f%n", size, time3);

        UnionFind4 uf4 = new UnionFind4(size);
        double time4 = testUF(uf4, m);
        System.out.printf("UnionFind4: size: %d, time:%f%n", size, time4);


        UnionFind5 uf5 = new UnionFind5(size);
        double time5 = testUF(uf5, m);
        System.out.printf("UnionFind5: size: %d, time:%f%n", size, time5);

    }
}
