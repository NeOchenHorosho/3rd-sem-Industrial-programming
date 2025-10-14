package studentset;
import java.io.*;
import java.util.Set;
import java.util.TreeSet;

public class StudentSet {
    private Set<Student> students;

    public StudentSet() {
        this.students = new TreeSet<>();
    }

    public void readFromFile(String filename) throws IOException {
        students.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    String[] parts = line.split(";");
                    if (parts.length == 4) {
                        int recordBook = Integer.parseInt(parts[0]);
                        String name = parts[1];
                        int group = Integer.parseInt(parts[2]);
                        float grade = Float.parseFloat(parts[3]);
                        students.add(new Student(recordBook, name, group, grade));
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Ошибка парсинга строки: " + line);
                }
            }
        }
    }

    public void writeToFile(String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Student student : students) {
                writer.write(student.toString());
                writer.newLine();
            }
        }
    }

    public StudentSet union(StudentSet otherSet) {
        StudentSet resultSet = new StudentSet();
        resultSet.students.addAll(this.students);
        resultSet.students.addAll(otherSet.students);
        return resultSet;
    }

    public StudentSet intersection(StudentSet otherSet) {
        StudentSet resultSet = new StudentSet();
        resultSet.students.addAll(this.students);
        resultSet.students.retainAll(otherSet.students);
        return resultSet;
    }

    public StudentSet difference(StudentSet otherSet) {
        StudentSet resultSet = new StudentSet();
        resultSet.students.addAll(this.students);
        // Удаляет из resultSet все элементы, которые есть в otherSet.students
        resultSet.students.removeAll(otherSet.students);
        return resultSet;
    }
    
    public void print() {
        for(Student s : students) {
            System.out.println(s);
        }
    }
}