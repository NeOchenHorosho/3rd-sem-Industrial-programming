package studentset;

public class Student implements Comparable<Student> {
    private int recordBookNumber;
    private String name;
    private int groupNumber;
    private float averageGrade;

    public Student(int recordBookNumber, String name, int groupNumber, float averageGrade) {
        this.recordBookNumber = recordBookNumber;
        this.name = name;
        this.groupNumber = groupNumber;
        this.averageGrade = averageGrade;
    }

    public int getRecordBookNumber() {
        return recordBookNumber;
    }

    public String getName() {
        return name;
    }

    public int getGroupNumber() {
        return groupNumber;
    }

    public float getAverageGrade() {
        return averageGrade;
    }

    @Override
    public String toString() {
        return recordBookNumber + ";" + name + ";" + groupNumber + ";" + averageGrade;
    }

    @Override
    public int compareTo(Student other) {
        return Integer.compare(this.recordBookNumber, other.recordBookNumber);
    }
}