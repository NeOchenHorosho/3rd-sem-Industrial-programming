import java.util.Scanner;

public class App {
    static boolean isLocalMaximum(int[][] arg, int x, int y){
        int n = arg.length, m = arg[0].length;
        for(int i = -1; i <= 1; i++){
            for(int j = -1; j <= 1; j++){
                if((i ==0 & j == 0) | (x+i < 0 | x+i > n | y+j <0 | y+j > m)) continue;
                if(arg[x][y] <= arg[x+i][y+j]) return false;
            }
        }
        return true;
    }
    static void swap(int a, int b){
        // кто бы мог подумать, что в java нельзя передать ссылку на аргумент
        a = a ^ b;  
        b = a ^ b;  
        a = a ^ b;  
    }
    static void printMatrix(int[][] m) {
    try {
        int rows = m.length;
        int columns = m[0].length;
        String str = "|\t";

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                str += m[i][j] + "\t";
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
        mainLoop: while (true) {
            int switchVar = scanner.nextInt();
            switch (switchVar) {
                case 0:
                    break mainLoop;
                case 1:{
                    // 22-14=9 значит буду делать 9, 23, 37
                    // задача 9
                    int rows, cols;
                    System.out.println("Введите количетсво строк и столбцов");
                    rows = scanner.nextInt();
                    cols = scanner.nextInt();
                    int[][] matrix = new int[rows][cols];
                    int resRow=0, minMaxConsecutive=cols, currentCountConsecutive=1, currentNumConsecutive=0;
                    for (int i = 0; i < rows; i++) {
                        for (int j = 0; j < cols; j++) {
                            System.out.print("Введите элемент [" + i + "][" + j + "]: ");
                            matrix[i][j] = scanner.nextInt();
                        }
                    }
                    
                    for(int i = 0; i < rows; i++)
                    {
                        currentNumConsecutive = matrix[i][0];
                        currentCountConsecutive = 1;
                        for(int j = 1; j < cols; j++)
                        {
                            if(currentNumConsecutive == matrix[i][j])
                                currentCountConsecutive++;
                            else
                            {
                                currentNumConsecutive = matrix[i][j];
                                if(currentCountConsecutive < minMaxConsecutive)
                                {
                                    minMaxConsecutive = currentCountConsecutive;
                                    resRow = i;
                                }
                                currentCountConsecutive = 1;
                                currentNumConsecutive = matrix[i][j];
                            }
                        }
                        if(currentCountConsecutive < minMaxConsecutive)
                            {
                                minMaxConsecutive = currentCountConsecutive;
                                resRow = i;
                            }
                    }
                    System.out.println("В строке " + resRow + " длинна максимальной серии последовательных символов минимальна и составляет " + minMaxConsecutive);
                    break;}
                case 2:{
                    // задача 23
                    
                        int rows, cols;
                        System.out.println("Введите количетсво строк и столбцов");
                        rows = scanner.nextInt();
                        cols = scanner.nextInt();
                        int[][] matrix = new int[rows][cols];
                        for (int i = 0; i < rows; i++) {
                            for (int j = 0; j < cols; j++) {
                                System.out.print("Введите элемент [" + i + "][" + j + "]: ");
                                matrix[i][j] = scanner.nextInt();
                            }
                        }
                        int xRes = -1, yRes = -1, minMaxVal = 0;
                        
                        for (int i = 0; i < rows-1; i++) {
                            for (int j = 0; j < cols-1; j++) {
                                if(isLocalMaximum(matrix, i, j))
                                {
                                    if(xRes == -1)
                                    {
                                        xRes = i;
                                        yRes = j;
                                        minMaxVal = matrix[i][j];
                                        break;
                                    }
                                    if(xRes == -1 | matrix[i][j] < minMaxVal)
                                    {
                                        xRes = i;
                                        yRes = j;
                                        minMaxVal = matrix[i][j];
                                    }
                                }
                            }
                        }
                        if(xRes == -1)
                        {
                            System.out.println("В вашей матрице нету локальных максимумов, а минимумов среди локальных максимумов и подавно");

                        }
                        else
                        {
                            System.out.println("Ого! Кажется элемент [" + xRes + "][" + yRes + "] - минимум среди локальных максимумов" );
                        }
                    }
                    break;
                case 3:{
                    // задача 37
                    int rows, cols;
                    System.out.println("Введите количетсво строк и столбцов");
                    rows = scanner.nextInt();
                    cols = scanner.nextInt();
                    int[][] matrix = new int[rows][cols];
                    for (int i = 0; i < rows; i++) {
                        for (int j = 0; j < cols; j++) {
                            System.out.print("Введите элемент [" + i + "][" + j + "]: ");
                            matrix[i][j] = scanner.nextInt();
                        }
                    }
                    int minX = 0, minY = 0, minVal = matrix[0][0];
                    for (int i = 0; i < rows; i++) {
                        for (int j = 0; j < cols; j++) {
                            if(matrix[i][j] < minVal)
                            {
                                minX = i;
                                minY = j;
                                minVal = matrix[i][j];
                            }
                        }
                    }
                    System.out.println("Исходная матрица:");
                    printMatrix(matrix);
                    int tmp;
                    for(int i = 0; i < rows; i++)
                    {
                        //swap(, );
                        tmp = matrix[i][minY]; 
                        matrix[i][minY] = matrix[i][cols-1];
                        matrix[i][cols-1] = tmp;
                    }
                    for(int j = 0; j < cols; j++)
                    {
                        //swap(matrix[minX][j], );
                        tmp = matrix[minX][j];
                        matrix[minX][j] = matrix[rows-1][j];
                        matrix[rows-1][j] = tmp;
                    }
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
