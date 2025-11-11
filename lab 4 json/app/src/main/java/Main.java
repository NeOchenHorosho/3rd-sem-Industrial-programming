import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();
        
        // Шаг 1: Читаем из input.txt
        System.out.println("Шаг 1: Чтение из input.txt...");
        readFromFile("input.txt", students);
        System.out.println("Прочитано студентов: " + students.size());
        
        // Шаг 2: Записываем в input.json
        System.out.println("\nШаг 2: Запись в input.json...");
        writeToJson("input.json", students);
        System.out.println("Данные записаны в input.json");
        
        // Шаг 3: Читаем из input.json
        System.out.println("\nШаг 3: Чтение из input.json...");
        List<Student> studentsFromJson = readFromJson("input.json");
        System.out.println("Прочитано студентов из JSON: " + studentsFromJson.size());
        
        // Шаг 4: Записываем в output.txt
        System.out.println("\nШаг 4: Запись в output.txt...");
        writeToTextFile("output.txt", studentsFromJson);
        System.out.println("Данные записаны в output.txt");
        
        // Сортировка и поиск
        Collections.sort(studentsFromJson);
        
        if (!studentsFromJson.isEmpty()) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("\nВведите фамилию студента для поиска: ");
            String searchLastName = scanner.nextLine();
            
            int index = binarySearchByLastName(studentsFromJson, searchLastName);
            if (index >= 0) {
                System.out.println("\nСтудент найден на позиции: " + index);
                printStudentInfo(studentsFromJson.get(index));
            } else {
                System.out.println("\nСтудент с фамилией \"" + searchLastName + "\" не найден");
            }
            
            scanner.close();
        }
    }

    private static void writeToJson(String filename, List<Student> students) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        
        try (Writer writer = new FileWriter(filename)) {
            gson.toJson(students, writer);
        } catch (IOException e) {
            System.err.println("Ошибка записи в JSON: " + e.getMessage());
        }
    }

    private static List<Student> readFromJson(String filename) {
        Gson gson = new Gson();
        
        try (Reader reader = new FileReader(filename)) {
            Type studentListType = new TypeToken<ArrayList<Student>>(){}.getType();
            return gson.fromJson(reader, studentListType);
        } catch (IOException e) {
            System.err.println("Ошибка чтения из JSON: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    private static void writeToTextFile(String filename, List<Student> students) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (int i = 0; i < students.size(); i++) {
                Student student = students.get(i);
                
                bw.write(String.format("STUDENT: %s,%s,%s,%d,%s%n",
                    student.getLastName(),
                    student.getFirstName(),
                    student.getMiddleName(),
                    student.getCourse(),
                    student.getGroup()
                ));
                
                for (Session session : student.getSessions()) {
                    bw.write(String.format("SESSION: %d%n", session.getSessionNumber()));
                    
                    for (Exam exam : session.getExams()) {
                        bw.write(String.format("EXAM: %s,%d%n",
                            exam.getSubject(),
                            exam.getGrade()
                        ));
                    }
                    
                    for (Credit credit : session.getCredits()) {
                        bw.write(String.format("CREDIT: %s,%b%n",
                            credit.getSubject(),
                            credit.isPassed()
                        ));
                    }
                }
                
                if (i < students.size() - 1) {
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка записи в текстовый файл: " + e.getMessage());
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
            System.out.println("\n✓ СТУДЕНТ-ОТЛИЧНИК");
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