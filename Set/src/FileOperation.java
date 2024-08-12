import java.io.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class FileOperation {

    public static boolean readFile(String filename, ArrayList<String> words){

        // Judge if filename or words valid
        if (filename == null || words == null) {
            System.out.println("filename is null or words is null");
            return false;
        }

        // If not, start reading files
        Scanner scanner;

        // Hint : Using Scanner requires IOException handling.
        try {
            File file = new File(filename);
            if (file.exists()) {
                // Start reading files
                FileInputStream fis = new FileInputStream(file);
                scanner = new Scanner(new BufferedInputStream(fis), "UTF-8");
                scanner.useLocale(Locale.ENGLISH);
            } else
                return false;
        } catch (IOException ioe) {
            System.out.println("cannot open: " + filename);
            return false;
        }

        if (scanner.hasNextLine()) {
            // Use String to store contents from file
            // "\\A" 用於匹配整個輸入的開頭，即字符串的開頭
            String contents = scanner.useDelimiter("\\A").next();

            // Use firstCharacterIndex() to find start index of string
            int start = firstCharacterIndex(contents, 0);

            for (int i = start + 1; i <= contents.length();) {

                if (i == contents.length() || !Character.isLetter(contents.charAt(i))) {
                    String word = contents.substring(start, i).toLowerCase();
                    words.add(word);

                    // Renew start's value
                    start = firstCharacterIndex(contents, i);
                    // Renew i's value with the offset of start
                    i = start + i;
                } else {
                    i ++;
                }
            }
        }

        return true;
    }

    private static int firstCharacterIndex(String s, int start) {

        for (int i = start; i < s.length(); i++) {
            if (Character.isLetter(s.charAt(i))) {
                return i;
            }
        }

        return s.length();
    }
}
