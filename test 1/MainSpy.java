import java.util.Scanner;

public class MainSpy {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int p = scanner.nextInt();

        String originalNumberStr = Integer.toString(p);
        int length = originalNumberStr.length();

        for (int i = 0; i < length; i++) {
            for (int j = 0; j <= 9; j++) {
                StringBuilder tempNumberStr = new StringBuilder(originalNumberStr);

                char newDigitChar = (char) ('0' + j);


                if (originalNumberStr.charAt(i) == newDigitChar) {
                    continue;
                }

                if (i == 0 && j == 0 && length > 1) {
                    continue;
                }

                tempNumberStr.setCharAt(i, newDigitChar);

                long newNum = Long.parseLong(tempNumberStr.toString());

                if (newNum % 9 == 0) {
                    System.out.println(newNum);
                }
            }
        }
        
        scanner.close();
    }
}