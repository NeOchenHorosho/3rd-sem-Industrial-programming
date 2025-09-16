import java.util.Scanner;
import expclass.ExpClass;
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
                    System.out.println("Отличный выбор! В какую степень от -10 до 10 вы хотите возвести число e?");
                    float x = scanner.nextFloat();
                    if(x<-10 | x>10)
                    {
                        System.out.println("не, не хочу считать");
                        break;
                    }
                    System.out.println("С какой степенью точности?");
                    int eps = scanner.nextInt();
                    
                    System.out.println(String.format("Ваш результат: %."+ Integer.toString(eps) +"f%n", ExpClass.exp(x, eps)));
                    System.out.println(String.format("Результат стандартной функции: %."+Integer.toString(eps)+"f%n", Math.exp(x)));
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
