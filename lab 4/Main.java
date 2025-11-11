import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();
        
        readFromFile("input.txt", students);
        
        Collections.sort(students);
        
        List<Student> excellentStudents = filterExcellentStudents(students);
        
        writeToFile("output.txt", excellentStudents);
        
        System.out.println("Найдено студентов-отличников: " + excellentStudents.size());
        
        if (!students.isEmpty()) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Введите фамилию студента для поиска: ");
            String searchLastName = scanner.nextLine();
            
            int index = binarySearchByLastName(students, searchLastName);
            if (index >= 0) {
                System.out.println("\nСтудент найден на позиции: " + index);
                printStudentInfo(students.get(index));
            } else {
                System.out.println("\nСтудент с фамилией \"" + searchLastName + "\" не найден");
            }
            
            scanner.close();
        }
    }

    private static void printStudentInfo(Student student) {
        System.out.println("\n========== ИНФОРМАЦИЯ О СТУДЕНТЕ ==========");
        System.out.println("Фамилия: " + student.getLastName());
        System.out.println("Имя: " + student.getFirstName());
        System.out.println("Отчество: " + student.getMiddleName());
        System.out.println("Курс: " + student.getCourse());
        System.out.println("Группа: " + student.getGroup());
        System.out.println("\n--- СЕССИИ ---");
        
        for (Session session : student.getSessions()) {
            System.out.println("\nСессия №" + session.getSessionNumber());
            
            if (!session.getExams().isEmpty()) {
                System.out.println("  Экзамены:");
                for (Exam exam : session.getExams()) {
                    System.out.println("    - " + exam.getSubject() + ": " + exam.getGrade());
                }
            }
            
            if (!session.getCredits().isEmpty()) {
                System.out.println("  Зачёты:");
                for (Credit credit : session.getCredits()) {
                    String status = credit.isPassed() ? "сдан" : "не сдан";
                    System.out.println("    - " + credit.getSubject() + ": " + status);
                }
            }
        }
        
        if (student.isExcellent()) {
            System.out.println("\nСТУДЕНТ ЯВЛЯЕТСЯ ОТЛИЧНИКОМ");
        }
        System.out.println("==========================================");
    }

    private static void readFromFile(String filename, List<Student> students) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            Student currentStudent = null;
            Session currentSession = null;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(":");
                if (parts.length < 2) continue;

                String key = parts[0].trim();
                String value = parts[1].trim();

                switch (key) {
                    case "STUDENT":
                        String[] studentData = value.split(",");
                        if (studentData.length == 5) {
                            currentStudent = new Student(
                                studentData[0].trim(),
                                studentData[1].trim(),
                                studentData[2].trim(),
                                Integer.parseInt(studentData[3].trim()),
                                studentData[4].trim()
                            );
                            students.add(currentStudent);
                        }
                        break;
                    case "SESSION":
                        if (currentStudent != null) {
                            currentSession = new Session(Integer.parseInt(value));
                            currentStudent.addSession(currentSession);
                        }
                        break;
                    case "EXAM":
                        if (currentSession != null) {
                            String[] examData = value.split(",");
                            if (examData.length == 2) {
                                currentSession.addExam(new Exam(
                                    examData[0].trim(),
                                    Integer.parseInt(examData[1].trim())
                                ));
                            }
                        }
                        break;
                    case "CREDIT":
                        if (currentSession != null) {
                            String[] creditData = value.split(",");
                            if (creditData.length == 2) {
                                currentSession.addCredit(new Credit(
                                    creditData[0].trim(),
                                    Boolean.parseBoolean(creditData[1].trim())
                                ));
                            }
                        }
                        break;
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
        }
    }

    private static List<Student> filterExcellentStudents(List<Student> students) {
        List<Student> excellent = new ArrayList<>();
        for (Student student : students) {
            if (student.isExcellent()) {
                excellent.add(student);
            }
        }
        return excellent;
    }

    private static void writeToFile(String filename, List<Student> students) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (Student student : students) {
                for (Session session : student.getSessions()) {
                    for (Exam exam : session.getExams()) {
                        bw.write(String.format("%s,%s,%s,%d,%s,%d,%s,%d%n",
                            student.getLastName(),
                            student.getFirstName(),
                            student.getMiddleName(),
                            student.getCourse(),
                            student.getGroup(),
                            session.getSessionNumber(),
                            exam.getSubject(),
                            exam.getGrade()
                        ));
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка записи в файл: " + e.getMessage());
        }
    }

    private static int binarySearchByLastName(List<Student> students, String lastName) {
        int left = 0;
        int right = students.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int comparison = students.get(mid).getLastName().compareTo(lastName);

            if (comparison == 0) {
                return mid;
            } else if (comparison < 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }
}