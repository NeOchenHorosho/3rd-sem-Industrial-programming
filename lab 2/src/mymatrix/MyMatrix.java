package mymatrix;
import java.io.FileWriter;
import java.util.Scanner;
import java.io.IOException;

public class MyMatrix
{
    public int[][] matrix;
    public MyMatrix()
    {
    }

    public void read_from_scanner(Scanner scanner)
    {
        int rows = scanner.nextInt();
        int cols = scanner.nextInt();
        matrix = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = scanner.nextInt();
            }
        }
    }
    public void write_to_filewriter(FileWriter writer) throws IOException
    {
        writer.write(matrix.length + " " + matrix[0].length + "\n");
        for (int[] is : matrix) {
            for (int i : is) {
                writer.write(i + " ");
            }
            writer.write("\n");
        }
    }
    public void first_task() {
        int resRow=0, minMaxConsecutive=matrix[0].length, currentCountConsecutive=1, currentNumConsecutive=0, currentRowMax;
        
        for(int i = 0; i < matrix.length; i++)
        {
            currentRowMax = 0;
            currentNumConsecutive = matrix[i][0];
            currentCountConsecutive = 1;       // 22-14=9 значит буду делать 9, 23, 37
            for(int j = 1; j < matrix[0].length; j++)
            {
                if(currentNumConsecutive == matrix[i][j])
                    currentCountConsecutive++;
                else
                {
                    currentNumConsecutive = matrix[i][j];
                    if(currentCountConsecutive > currentRowMax)
                    {
                        currentRowMax = currentCountConsecutive;
                    }
                    currentCountConsecutive = 1;
                    currentNumConsecutive = matrix[i][j];
                }
            }
            if(currentCountConsecutive > currentRowMax)
            {
                currentRowMax = currentCountConsecutive;
            }
            if(currentRowMax < minMaxConsecutive)
            {
                minMaxConsecutive = currentRowMax;
                resRow = i;
            }
        }
        System.out.println("В строке " + resRow + " длинна максимальной серии последовательных символов минимальна и составляет " + minMaxConsecutive);
    }


    static boolean isLocalMaximum(MyMatrix arg, int x, int y){
        int n = arg.matrix.length, m = arg.matrix[0].length;
        for(int i = -1; i <= 1; i++){
            for(int j = -1; j <= 1; j++){
                if((i ==0 & j == 0) | (x+i < 0 | x+i > n | y+j <0 | y+j > m)) continue;
                if(arg.matrix[x][y] <= arg.matrix[x+i][y+j]) return false;
            }
        }
        return true;
    }
    public void second_task() {
        
        int xRes = -1, yRes = -1, minMaxVal = 0;
        
        for (int i = 0; i < matrix.length-1; i++) {
            for (int j = 0; j < matrix[0].length-1; j++) {
                if(isLocalMaximum(this, i, j))
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
    public void third_task() {
        int minX = 0, minY = 0, minVal = matrix[0][0];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if(matrix[i][j] < minVal)
                {
                    minX = i;
                    minY = j;
                    minVal = matrix[i][j];
                }
            }
        }
        int tmp;
        for(int i = 0; i < matrix.length; i++)
        {
            tmp = matrix[i][minY]; 
            matrix[i][minY] = matrix[i][matrix[0].length-1];
            matrix[i][matrix[0].length-1] = tmp;
        }
        int[] arr_tmp;
        arr_tmp = matrix[minX];
        matrix[minX] = matrix[matrix.length-1];
        matrix[matrix.length-1] = arr_tmp;
        
    }
}