import java.io.IOException;
import java.util.Scanner;
import studentset.*;
public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            
            System.out.print("Введите имя первого файла для чтения: ");
            String file1 = scanner.nextLine();

            System.out.print("Введите имя второго файла для чтения: ");
            String file2 = scanner.nextLine();

            StudentSet set1 = new StudentSet();
            StudentSet set2 = new StudentSet();

            try {
                set1.readFromFile(file1);
                set2.readFromFile(file2);
            } catch (IOException e) {
                System.err.println("Ошибка при чтении файла: " + e.getMessage());
                return;
            }

            System.out.print("Введите операцию (union, intersection, difference): ");
            String operation = scanner.nextLine().toLowerCase().trim();

            StudentSet resultSet = null;

            switch (operation) {
                case "union":
                    resultSet = set1.union(set2);
                    break;
                case "intersection":
                    resultSet = set1.intersection(set2);
                    break;
                case "difference":
                    resultSet = set1.difference(set2);
                    break;
                default:
                    System.err.println("Неизвестная операция: " + operation);
                    return;
            }

            System.out.println("Результат операции '" + operation + "':");
            resultSet.print();

            System.out.print("\nВведите имя файла для записи результата: ");
            String outputFile = scanner.nextLine();

            try {
                resultSet.writeToFile(outputFile);
            } catch (IOException e) {
                System.err.println("Ошибка при записи в файл: " + e.getMessage());
            }

        }
    }
}