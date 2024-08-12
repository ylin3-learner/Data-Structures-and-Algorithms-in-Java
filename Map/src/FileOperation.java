import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileOperation {

    public static boolean readFile(String filename, ArrayList<String> words) throws IOException {
        if (filename == null || words == null) {
            System.out.println("Filename or words list is null.");
            return false;
        }

        File file = new File(filename);

        if (!file.exists()) {
            System.out.println("File " + filename + " not found.");
            return false;
        }

        try (Scanner scanner = new Scanner(file, "UTF-8")) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                processLine(line, words);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return true;
    }

    private static void processLine(String line, ArrayList<String> words) {
        StringBuilder wordBuilder = new StringBuilder();



        for (char c : line.toCharArray()) {
            if (Character.isLetter(c)) {
                wordBuilder.append(c);
            } else if (wordBuilder.length() > 0) {
                words.add(wordBuilder.toString().toLowerCase());
                wordBuilder.setLength(0);  // 清空 StringBuilder
            }
        }


        // 檢查行末尾是否有單詞
        if (wordBuilder.length() > 0) {
            words.add(wordBuilder.toString().toLowerCase());
        }
    }
}