import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TextJustify {
    private static final int INDENT_SIZE = 4; 
    
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        int width = sc.nextInt();
        sc.close();
        if (width < 1)
        {
            System.err.println("Неверная длинна потока");
            System.exit(1);
        }
        
        FileWorker io = new FileWorker();
        List<String> inputLines = io.readAllLines("input.txt");

        
        List<String> result = justifyDocument(inputLines, width);

        
        io.writeAllLines("output.txt", result);
    }

    
    private static List<String> justifyDocument(List<String> lines, int width) {
        List<String> out = new ArrayList<>();
        List<String> paragraphWords = new ArrayList<>();

        for (String raw : lines) {
            String line = raw == null ? "" : raw;
            if (line.trim().isEmpty()) {
                flushParagraph(paragraphWords, width, out);
                out.add(""); 
            } else {
                
                for (String w : line.trim().split("\\s+")) {
                    if (!w.isEmpty()) paragraphWords.add(w);
                }
            }
        }
        
        flushParagraph(paragraphWords, width, out);

        return out;
    }

    
    private static void flushParagraph(List<String> words, int width, List<String> out) {
        if (words.isEmpty()) return;

        int i = 0;
        boolean isFirstLineOfParagraph = true; 
        
        while (i < words.size()) {
            
            int availableWidth = isFirstLineOfParagraph ? width - INDENT_SIZE : width;
            
            String first = words.get(i);
            int sumLen = first.length();
            int j = i + 1;

            if (sumLen > availableWidth) {
                String w = first;
                int pos = 0;
                int n = w.length();

                while (n - pos > availableWidth) {
                    String lineContent = w.substring(pos, pos + availableWidth - 1) + '-';
                    if (isFirstLineOfParagraph) {
                        out.add("    " + lineContent); 
                        isFirstLineOfParagraph = false;
                    } else {
                        out.add(lineContent);
                    }
                    pos += availableWidth - 1;
                }

                int rem = n - pos;
                if (rem == availableWidth) {
                    String lineContent = w.substring(pos, pos + availableWidth);
                    if (isFirstLineOfParagraph) {
                        out.add("    " + lineContent);
                        isFirstLineOfParagraph = false;
                    } else {
                        out.add(lineContent);
                    }
                    i++; 
                    continue;
                } else {
                    words.set(i, w.substring(pos));
                    sumLen = words.get(i).length();
                    j = i + 1;
                }
            }

            
            while (j < words.size()) {
                int nextLen = sumLen + 1 + words.get(j).length();
                if (nextLen > availableWidth) break;
                sumLen = nextLen;
                j++;
            }

            List<String> lineWords = words.subList(i, j);
            boolean isLastLineOfParagraph = (j == words.size());
            String line = buildLine(lineWords, availableWidth, isLastLineOfParagraph);
            
            
            if (isFirstLineOfParagraph) {
                out.add("    " + line);
                isFirstLineOfParagraph = false;
            } else {
                out.add(line);
            }

            i = j;
        }

        words.clear();
    }
    
    private static String buildLine(List<String> words, int width, boolean lastLine) {
        if (words.size() == 1 || lastLine) {
            
            return String.join(" ", words);
        }

        int gaps = words.size() - 1;
        int sumWords = 0;
        for (String w : words) sumWords += w.length();

        
        int extraSpaces = width - (sumWords + gaps); 
        int base = extraSpaces / gaps;
        int rem = extraSpaces % gaps;

        StringBuilder sb = new StringBuilder(width);
        for (int k = 0; k < words.size(); k++) {
            sb.append(words.get(k));
            if (k < gaps) {
                int spacesHere = 1 + base + (k < rem ? 1 : 0);
                repeatSpace(sb, spacesHere);
            }
        }
        return sb.toString();
    }

    private static void repeatSpace(StringBuilder sb, int count) {
        for (int i = 0; i < count; i++) sb.append(' ');
    }
}

class FileWorker {
    public List<String> readAllLines(String filename) throws IOException {
        return Files.readAllLines(Path.of(filename), StandardCharsets.UTF_8);
    }

    public void writeAllLines(String filename, List<String> lines) throws IOException {
        Files.write(Path.of(filename), lines, StandardCharsets.UTF_8);
    }
}