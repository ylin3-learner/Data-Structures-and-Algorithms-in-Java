public class SubstringMatchHelper {

    private SubstringMatchHelper() {}

    public static void testMatch(String name, String s, String t) {

        if (name == null || s == null || t == null)
            throw new IllegalArgumentException("name or s or t cannot be null");

        // How to get match result? Set up pos variable
        int pos = -1;

        long startTime = System.nanoTime();

        if (name.equals("bruteforce")) {
            pos = SubstringMatch.bruteforce(s, t);
        } else if (name.equals("rabinKarp")) {
            pos = SubstringMatch.rabinKarp(s, t);
        } else if (name.equals("kmp")) {
            pos = SubstringMatch.kmp(s, t);
        }

        long endTime = System.nanoTime();

        double time = (endTime - startTime) / 100000000.0;

        // Testify matching algorithm
        if (s.indexOf(t) != pos)
            throw new RuntimeException(name + " failed.");

        System.out.println(String.format("%s, res = %d, time = %f s", name, pos, time));
    }
}
