public class Student {
    private String name;

    public Student(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object student) {
        // Check if compare the same object
        if(this == student)
            return true;
        // Check if this student is not null
        if(student == null)
            return false;

        if (this.getClass() != student.getClass())
            return false;
        // Why we still need casting after checking it's a student class ?
        // Compiler still think student is the object class, not student class
        // How to test?
        // student.name - Compiling error: Only Student has the attribute of name, not Object
        Student another = (Student) student;
        return this.name.equalsIgnoreCase(another.name);
    }
}
