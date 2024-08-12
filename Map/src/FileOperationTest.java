import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class FileOperationTest {

    public static boolean readFile(String filename, ArrayList<String> words) {

        if (filename == null || words == null) {
            System.out.println("filename or words is empty.");
            return false;
        }

        File file = new File(filename);

        Scanner scanner;

        try {
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                scanner = new Scanner(new BufferedInputStream(fis), "UTF-8");
                scanner.useLocale(Locale.ENGLISH);
            } else
                return false;
        } catch (IOException ioe) {
            System.out.println("File can not be found!");
            return false;
        }

        if (scanner.hasNextLine()) {
            // 將 Scanner 的分隔符設置為開頭（即整個輸入作為一個 token）
            String contents = scanner.useDelimiter("\\A").next();
            // 調用了之前定義的 firstCharacterIndex 方法，以查找 contents 中第一個字母的索引。
            int start = firstCharacterIndex(contents, 0);

            // 假設該方法返回了字串的長度，即 start = contents.length()
            // 即使進入了 while 迴圈，但由於在第三步中的 firstCharacterIndex 方法返回了字串的長度，
            // i 的初始值為 start + 1，即 i = contents.length() + 1，但由於條件 i <= contents.length() 不滿足
            // 所以迴圈體不會執行，直接跳出了迴圈。
            for (int i = start + 1; i <= contents.length(); ) {
                // 這個條件判斷當前索引 i 是否等於 contents 的長度，或者當前字符是否不是字母。這樣做是為了確定是否找到了一個單詞的結尾。
                if (i == contents.length() || !Character.isLetter(contents.charAt(i))) {
                    // 如果找到了單詞的結尾，則通過 substring 方法從 contents 中提取單詞，然後將其轉換為小寫，並將其存儲在 word 變量中。
                    String word = contents.substring(start, i).toLowerCase();
                    words.add(word);
                    // 調用 firstCharacterIndex 方法找到下一個單詞的起始索引，並將其設置為 start
                    start = firstCharacterIndex(contents, i);
                    i = start + 1;
                } else
                    i ++;
            }

        }
        scanner.close();
        return true;
    }

    private static int firstCharacterIndex(String s, int start) {

        for (int i = start; i < s.length(); i++) {
            if (Character.isLetter(s.charAt(i)))
                return i;
            // 如果找到一個字母，則返回該字母的索引，即變量 i 的值。
        }
        // 如果在整個字串中沒有找到任何字母，則返回字串的長度，這意味著沒有找到字母。
        return s.length();
    }
}
