import java.util.ArrayList;
import java.util.List;

public class Student implements Comparable<Student> {
    private String lastName;
    private String firstName;
    private String middleName;
    private int course;
    private String group;
    private List<Session> sessions;

    public Student(String lastName, String firstName, String middleName, 
                   int course, String group) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.course = course;
        this.group = group;
        this.sessions = new ArrayList<>();
    }

    public void addSession(Session session) {
        sessions.add(session);
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public int getCourse() {
        return course;
    }

    public String getGroup() {
        return group;
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public boolean isExcellent() {
        if (sessions.isEmpty()) {
            return false;
        }
        for (Session session : sessions) {
            if (!session.isExcellent()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int compareTo(Student other) {
        return this.lastName.compareTo(other.lastName);
    }
}