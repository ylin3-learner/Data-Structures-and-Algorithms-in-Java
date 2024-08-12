import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileCopy {
    public static void main(String[] args) throws IOException {

        String p1 = "A.gif";
        String p2 = "B.gif";

        // Encapsulate read-write objects
        FileInputStream f1 = new FileInputStream(p1);
        FileOutputStream f2 = new FileOutputStream(p2);

        // Copy
        byte[] b = new byte[1024];
        int len = 0;
        while ((len = f1.read(b)) != -1) {
            f2.write(b, 0, len);
        }
    }
}
