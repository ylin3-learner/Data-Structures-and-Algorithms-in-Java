import java.util.Random;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {

        final int TOTAL_STUDENTS = (int)Math.pow(26, 4);
        Student[] students = new Student[TOTAL_STUDENTS];
        Random rnd = new Random();

        IntStream.range(0, TOTAL_STUDENTS).forEach(index -> {
            String name = generateName(index);
            int score = rnd.nextInt(101);  // 0-100
            students[index] = new Student(name, score);
        });

        // 計數排序過程
        int R = 101;

        // 紀錄 數量
        // O(n)
        int[] cnt = new int[R];
        for (Student student: students) {
            if (student != null)
                cnt[student.getScore()] ++;   // 統計每個分數出現的次數
        }

        // 從 0 到 100 每個分數的出現次數
        for (int e: cnt)
            System.out.print(e + " ");


        // 計算每個分數的左右邊界 轉為 index (前綴樹), 方便排序
        // O(R)
        int[] index = new int[R + 1];
        for (int i = 0; i < R; i++)
            index[i+1] = index[i] + cnt[i];

        System.out.println();
        for (int e: index)
            System.out.print(e + " ");

        // 開輔助空間, 防止覆蓋原空間 students, 保存除了分數以外的學生信息
        Student[] temp = new Student[TOTAL_STUDENTS];

        // 計數排序
        // O(n)
        for (Student student: students) {
            if (student != null) {
                // 使用 index[student.getScore()] 查找該學生需要放置的位置索引
                temp[index[student.getScore()]] = student;
                // 如果下次看到同樣的分數, 放置的位置須往後挪一位
                index[student.getScore()]++;
            }
        }

        // O(n)
        for (int i = 0; i < TOTAL_STUDENTS; i++)
            students[i] = temp[i];

        // 整體: O(n + R) --> 所以如果想要讓時間複雜度控制在 O(n), R 必須小於 n -> 數值(R)範圍小

        // 驗證計數排序
        for (int i = 1; i < TOTAL_STUDENTS; i++) {

            // 驗證排序有效性
            if (students[i - 1].getScore() > students[i].getScore())
                throw new RuntimeException("Sort failed.");

            // 驗證排序穩定性 -- 穩定
            if (students[i-1].getScore() == students[i].getScore()) {

                if (students[i-1].getName().compareTo(students[i].getName()) > 0)
                    throw new RuntimeException("Non-stable counting sort.");
            }
        }

    }

    public static String generateName(int index) {
        char c1 = (char) ('a' + (index / (26 * 26 * 26) % 26));
        char c2 = (char) ('a' + (index / (26 * 26) % 26));
        char c3 = (char) ('a' + ((index / 26) % 26));
        char c4 = (char) ('a' + index % 26);

        return "" + c1 + c2 + c3 + c4;
    }
}
