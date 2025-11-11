import java.util.ArrayList;
import java.util.List;

public class Session {
    private int sessionNumber;
    private List<Exam> exams;
    private List<Credit> credits;

    public Session(int sessionNumber) {
        this.sessionNumber = sessionNumber;
        this.exams = new ArrayList<>();
        this.credits = new ArrayList<>();
    }

    public void addExam(Exam exam) {
        exams.add(exam);
    }

    public void addCredit(Credit credit) {
        credits.add(credit);
    }

    public int getSessionNumber() {
        return sessionNumber;
    }

    public List<Exam> getExams() {
        return exams;
    }

    public List<Credit> getCredits() {
        return credits;
    }

    public boolean isExcellent() {
        for (Exam exam : exams) {
            if (!exam.isPassed()) {
                return false;
            }
        }
        for (Credit credit : credits) {
            if (!credit.isPassed()) {
                return false;
            }
        }
        return true;
    }
}