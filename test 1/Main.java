import hotel.*;
import java.io.IOException;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            
            System.out.print("Введите имя первого файла для чтения: ");
            String file1 = scanner.nextLine();
            HotelSet hotels = new HotelSet();

            try {
                hotels.readFromFile(file1);
            } catch (IOException e) {
                System.err.println("Ошибка при чтении файла: " + e.getMessage());
                return;
            }

            System.out.print("Введите операцию (1.Все отели. 2.Отели по городу. 3.Отели по названию)): ");
            String operation = scanner.nextLine().trim();

            HotelSet resultSet = null;

            switch (operation) {
                case "1":
                    hotels.print();
                    break;
                case "2":
                    System.out.println("Введите названия города: ");
                    String cityName = scanner.nextLine().trim();  
                    hotels.printByCity(cityName);
                    break;
                case "3":
                    System.out.println("Введите названия отеля: ");
                    String hotelName = scanner.nextLine().trim();  
                    hotels.printByHotelName(hotelName);
                    break;
                default:
                    System.err.println("Неизвестная операция: " + operation);
                    return;
            }

        }
    }
}
