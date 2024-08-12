public class Student {

    private String name;
    private int score;

    public Student(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return this.name;
    }

    public int getScore() {
        return this.score;
    }

    // Student(name: %s, score:%d)
    @Override
    public String toString() {
        return String.format("Student(name: %s, score: %d)", getName(), getScore());
    }
}
