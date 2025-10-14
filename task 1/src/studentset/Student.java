package studentset;
import java.util.Objects;

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

    // Геттеры для доступа к полям
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

    /**
     * Преобразует объект Student в строку для записи в файл.
     * Разделитель - точка с запятой (;).
     */
    @Override
    public String toString() {
        return recordBookNumber + ";" + name + ";" + groupNumber + ";" + averageGrade;
    }

    /**
     * Основной метод для сравнения студентов.
     * TreeSet использует его для упорядочивания элементов и определения уникальности.
     */
    @Override
    public int compareTo(Student other) {
        // Сравниваем студентов по номеру зачётки
        return Integer.compare(this.recordBookNumber, other.recordBookNumber);
    }

    /**
     * equals и hashCode также переопределены для консистентности с compareTo.
     * Два студента считаются равными, если у них одинаковый номер зачётки.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return recordBookNumber == student.recordBookNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(recordBookNumber);
    }
}