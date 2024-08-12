public class Student implements Comparable<Student>{

    private String name;
    private int score;

    public Student(String name, int score) {
        this.name = name;
        this.score = score;
    }

    @Override
    public int compareTo(Student another) {

        return another.score - this.score;
    }

    @Override
    public boolean equals(Object student) {

        if (this == student)
            return true;

        if (student == null)
            return false;

        if (this.getClass() != student.getClass())
            return false;

        Student another = (Student) student;
        return this.name.equalsIgnoreCase(another.name);
    }

    @Override
    public String toString() {
        return String.format("Student(name= %s, score=%d)", name, score);
    }

    public static void main(String[] args) {

        Array<Student> arr = new Array<>();
        arr.addLast(new Student("Alice", 99));
        arr.addLast(new Student("Bobo", 90));
        arr.addLast(new Student("Tony", 60));
        System.out.println(arr);
    }
}
