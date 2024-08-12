import java.util.HashMap;

public class Main {

    public static void main(String[] args) {

        int a = 42;
        System.out.println(((Integer)a).hashCode());

        int b = -42;
        System.out.println(((Integer)b).hashCode());

        double PI = 3.1415926;
        System.out.println(((Double) PI).hashCode());

        String s = "Imooc";
        System.out.println(s.hashCode());

        Student stu = new Student(4, 11, "youLun", "Lin");
        System.out.println(stu.hashCode());

        HashMap<Student, Integer> scores = new HashMap<>();
        scores.put(stu, 100);
    }
}
