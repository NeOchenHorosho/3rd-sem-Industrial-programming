public class Exam {
    private String subject;
    private int grade;

    public Exam(String subject, int grade) {
        this.subject = subject;
        this.grade = grade;
    }

    public String getSubject() {
        return subject;
    }

    public int getGrade() {
        return grade;
    }

    public boolean isPassed() {
        return grade >= 9;
    }
}