import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class App{
    static final String VOWELS = "аеёиоуыэюяaeiou";
    static final String CONSONANTS = "wrtpsdfghjklzxcvbnmйцкнгшщзхфвпрлджчсмтб";


    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));

        StringBuilder sb = new StringBuilder();
        while (true) {
            String line = br.readLine();
            if (line == null || line.isEmpty()) break;
            if (sb.length() > 0) sb.append('\n');
            sb.append(line);
        }

        String text = sb.toString();
        if (text.isEmpty()) {
            System.out.println("Текст не введён.");
            return;
        }

        String[] sentences = text.split("\\.");
        int idx = 1;
        for (String s : sentences) {
            if (s.isEmpty()) continue; 

            int vowels = 0, consonants = 0;

            for (char ch : s.toCharArray()) {
                if (isVowel(ch)) {
                    vowels++;
                } else if (isConsonant(ch)) {
                    consonants++;
                }
            }

            String verdict;
            if (vowels > consonants) {
                verdict = "больше гласных";
            } else if (consonants > vowels) {
                verdict = "больше согласных";
            } else {
                verdict = "поровну";
            }

            System.out.printf("Предложение %d: гласных %d, согласных %d — %s.%n",
                    idx++, vowels, consonants, verdict);
        }
    }

    private static boolean isVowel(char ch) {
        return VOWELS.contains(Character.toLowerCase(ch)+"");
    }

    private static boolean isConsonant(char ch) {
        return CONSONANTS.contains(Character.toLowerCase(ch)+"");
    }
}