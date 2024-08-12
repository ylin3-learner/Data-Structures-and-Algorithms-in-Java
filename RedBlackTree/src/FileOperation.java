import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class FileOperation {

    public static boolean readFile(String filename, ArrayList<String> words)  {

        if (filename == null || words == null)
            throw new RuntimeException(filename + " or words not exist!");

        File file = new File(filename);
        try (FileInputStream fis = new FileInputStream(file);
             BufferedInputStream bis = new BufferedInputStream(fis);
             Scanner scanner = new Scanner(bis, "UTF-8")) {

            scanner.useLocale(Locale.ENGLISH);

            if (scanner.hasNextLine()) {
                String content = scanner.useDelimiter("\\A").next();
                int start = firstCharacterIndex(content, 0);

                for (int i = start + 1; i <= content.length(); ) {
                    if (i == content.length() || !Character.isLetter(content.charAt(i))) {
                        String word = content.substring(start, i).toLowerCase();
                        words.add(word);

                        start = firstCharacterIndex(content, i);
                        i = start + 1;
                    } else {
                        i++;
                    }
                }
            }
            return true;
        } catch (FileNotFoundException fileNotFoundException) {
            System.out.println(fileNotFoundException.getMessage());
            return false;
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
            return false;
        }
    }

    private static int firstCharacterIndex(String content, int start) {
        for (int i = start; i < content.length(); i++) {
            if (Character.isLetter(content.charAt(i)))
                return i;
        }
        return content.length();
    }
}
