import mymatrix.MyMatrix;
import java.io.File;
import java.util.Scanner;


public class App {
    static void printMatrix(MyMatrix m) {
    try {
        int rows = m.matrix.length;
        int columns = m.matrix[0].length;
        String str = "|\t";

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                str += m.matrix[i][j] + "\t";
            }
            System.out.println(str + "|");
            str = "|\t";
        }
    } catch (Exception e) {
        System.out.println("Matrix is empty!!");
    }
}
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите номер задания (1,2,3). Для выхода введите 0");
        MyMatrix matrix = new MyMatrix();
        mainLoop: while (true) {
            int switchVar = scanner.nextInt();
            switch (switchVar) {
                case 0:
                    break mainLoop;
                case 1:{
                    // задача 9
                    System.out.println("Введите название файла, из которого вы хотите прочесть матрицу");
                    scanner.nextLine();
                    String filename = scanner.nextLine();
                    Scanner fin = new Scanner(new File(filename));
                    
                    matrix.read_from_scanner(fin);
                    fin.close();
                    matrix.first_task();
                    break;}
                case 2:{
                    // задача 23
                    
                    System.out.println("Введите название файла, из которого вы хотите прочесть матрицу");
                    scanner.nextLine();
                    String filename = scanner.nextLine();
                    Scanner fin = new Scanner(new File(filename));
                    
                    matrix.read_from_scanner(fin);
                    fin.close();
                    matrix.second_task();
                    }
                    break;
                case 3:{
                    // задача 37
                    System.out.println("Введите название файла, из которого вы хотите прочесть матрицу");
                    scanner.nextLine();
                    String filename = scanner.nextLine();
                    Scanner fin = new Scanner(new File(filename));
                    
                    matrix.read_from_scanner(fin);
                    fin.close();
                    
                    
                    System.out.println("Исходная матрица:");
                    printMatrix(matrix);

                    matrix.third_task();
                    System.out.println("Результат:");
                    printMatrix(matrix);
                }
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
