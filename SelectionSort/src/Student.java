public class Student implements Comparable <Student>{
    private String name;
    private int score;

    public Student (String name, int score) {
        this.name = name;
        this.score = score;
    }

    @Override
    public int compareTo(Student another) {

//        if (this.score < another.score)
//            return -1;
//        else if (this.score == another.score)
//            return 0;
//        else
//            return 1;
        return this.score - another.score;
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
        return String.format("Student(name: %s, score: %d)", name, score);
    }
}
