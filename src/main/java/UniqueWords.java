import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class UniqueWords {
    //File path
    private static final String TEST_FILE = "src/main/resources/testFile.txt";
    private static long WORD_COUNTER = 0;

    //Generate random words to file
    private String generateRandomWord() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    private void generateMassiveTextFile() {
        try (PrintWriter writer = new PrintWriter(TEST_FILE, StandardCharsets.UTF_8)) {
            for (int i = 0; i < 1_000_000; i++) {
                writer.print(generateRandomWord() + " ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //Read, count  and creating map of unique words
    private Map<String, Integer> wordsReaderAndCounter() {

    //Saving our unique words to "wordsMap"
        Map<String, Integer> wordsMap = new HashMap<>();
        //Load the file
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        new DataInputStream(
                                new FileInputStream(TEST_FILE))))) {
            //Parsing, cleaning up, and write down the words from TEST_FILE
            br.lines().forEach(line -> {
                StringTokenizer st = new StringTokenizer(line, "\",.; ");
                // Use a while loop to check if there are any words in TEST_FILE
                while (st.hasMoreTokens()) {
                    //Convert words to lower case
                    String tmp = st.nextToken().toLowerCase();
                    if (wordsMap.containsKey(tmp)) {
                        wordsMap.put(tmp, wordsMap.get(tmp) + 1);
                    } else {
                        wordsMap.put(tmp, 1);
                    }
                    WORD_COUNTER++;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wordsMap;
    }

    private Long memoryConverter(Long bytes) {
        long size = 1024;
        long toMB = bytes / size;
        return toMB / size;
    }
    private Double timeConverter(Long bytes) {
        return bytes * 0.001;
    }

    public static void main(String[] args) throws InterruptedException {
        UniqueWords uniqueWords = new UniqueWords();
        //Count total memory at the start of program
        long start = System.currentTimeMillis();
        System.out.println("Total JVM memory before start: " + uniqueWords.memoryConverter(Runtime.getRuntime().totalMemory()) + " mb");
        Thread.sleep(3000);
        //Generate 1mln unique words to file
        uniqueWords.generateMassiveTextFile();
        //Read and print unique words
        Map<String, Integer> wordMap = uniqueWords.wordsReaderAndCounter();
        wordMap.forEach((key, value) -> System.out.println(key + " - " + value));

        //Count total memory at the end of program
        long finish = System.currentTimeMillis();
        System.out.println("Total JVM memory after finish: " + uniqueWords.memoryConverter(Runtime.getRuntime().totalMemory()) + " mb");
        System.out.println("Total execution time of the program: " + uniqueWords.timeConverter(finish - start) + " seconds");
        System.out.println("File consist of: " + WORD_COUNTER + " words");

        uniqueWords.generateRandomWord();
    }
}
