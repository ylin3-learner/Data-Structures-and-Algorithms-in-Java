import java.io.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class FileOperation {

    private FileOperation() {}

    public static boolean readFile(String filename, ArrayList<String> words) {

        if (filename == null || words == null) {
            throw new IllegalArgumentException("filename is null or words is null!");
        }

        // Read file
        Scanner scanner;

        try {
            File file = new File((filename));
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                scanner = new Scanner(new BufferedInputStream(fis), "UTF-8");
                scanner.useLocale(Locale.ENGLISH);
            } else
                return false;
        } catch (IOException ioe) {
            System.out.println(filename + "cannot be opened!");
            return false;
        }

        if (scanner.hasNextLine()) {

            String contents = scanner.useDelimiter("\\A").next();

            // How to divide contents into words? Find the index of first character
            int start = firstCharacterIndex(contents, 0);

            for (int i = start + 1; i <= contents.length(); i++) {

                if (i == contents.length() || !Character.isLetter(contents.charAt(i))) {

                    String word = contents.substring(start, i).toLowerCase();
                    words.add(word);

                    start = firstCharacterIndex(contents, i);
                    i = start + 1;
                } else
                    i++;
            }
        }

        return true;
    }

    public static String readFile(String filename) {

        if (filename == null)
            throw new IllegalArgumentException("filename is null");

        ArrayList<String> words = new ArrayList<>();
        // concatenate words in contents

        if (readFile(filename, words)) {

            StringBuilder sb = new StringBuilder();
            for (String word: words)
                sb.append(word);
            return sb.toString();
        }

        throw new RuntimeException("Error in handling" + filename);
    }

    private static int firstCharacterIndex(String content, int start) {

        if (content == null) return -1;

        for (int i = start; i < content.length(); i++) {
            if (Character.isLetter(content.charAt(i)))
                return i;
        }

        return content.length();
    }
}
