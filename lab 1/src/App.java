import java.util.Scanner;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите номер задания. Для выхода введите 0");
        mainLoop: while (true) {
            int switchVar = scanner.nextInt();
            switch (switchVar) {
                case 0:
                    break mainLoop;
                case 1:
                    System.out.println("Отличный выбор! В какую степень вы хотите возвести число e?");
                    float x = scanner.nextFloat();
                    System.out.println("С какой степенью точности?");
                    double eps = scanner.nextInt(), result = 0, temp = 1;
                    eps = Math.pow(10, -eps);
                    for(int count = 1; temp> eps; count++)
                    {
                        result += temp;
                        temp*= x/count;
                    }
                    System.out.println(String.format("Ваш результат: %.3f%n", result));
                    System.out.println(String.format("Результат стандартной функции: %.3f%n", Math.exp(x)));
                    break;
                default:
                    System.out.println("Неверный номер задания. Введите номер задания повторно");
                    switchVar = scanner.nextInt();
                    break;
            }
        }
        scanner.close();
    }
}
