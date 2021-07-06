import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

public class UniqueWords {
    private static final String WORD_FILE = "C:\\Users\\Yurko\\Desktop\\mlnwords.txt";
    private static long MAX_TOTAL_MEMORY_USED = 0;
    private Map<String, Integer> wordsReaderAndCounter() {

        FileInputStream fis;

        DataInputStream dis;

        BufferedReader br = null;

        Map<String, Integer> wordsMap = new HashMap<>();

        try {

            fis = new FileInputStream(WORD_FILE);

            dis = new DataInputStream(fis);

            br = new BufferedReader(new InputStreamReader(dis));

            String line;

            while ((line = br.readLine()) != null) {

                if (MAX_TOTAL_MEMORY_USED < Runtime.getRuntime().totalMemory())
                    MAX_TOTAL_MEMORY_USED = Runtime.getRuntime().totalMemory();

                StringTokenizer st = new StringTokenizer(line , "\",.; ");

                while (st.hasMoreTokens()) {

                    if (MAX_TOTAL_MEMORY_USED < Runtime.getRuntime().totalMemory())
                        MAX_TOTAL_MEMORY_USED = Runtime.getRuntime().totalMemory();

                    String tmp = st.nextToken().toLowerCase();

                    if (wordsMap.containsKey(tmp)) {

                        wordsMap.put(tmp , wordsMap.get(tmp) + 1);

                    } else {

                        wordsMap.put(tmp , 1);
                    }
                }
            }

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            try {

                if (br != null) {

                    br.close();
                }

            } catch (Exception e) {

                e.printStackTrace();
            }
        }

        return wordsMap;
    }
    private List<Map.Entry<String, Integer>> sortByValue(Map<String, Integer> mapForSorting) {

        Set<Map.Entry<String, Integer>> set = mapForSorting.entrySet();

        List<Map.Entry<String, Integer>> list = new ArrayList<>(set);

        list.sort((o1 , o2) -> (o2.getValue()).compareTo(o1.getValue()));

        return list;
    }


    public static void main(String[] args) {

        UniqueWords uniqueWords = new UniqueWords();

        long start = System.currentTimeMillis();

        System.out.println("Total JVM memory before start: " + Runtime.getRuntime().totalMemory());

        Map<String, Integer> wordMap = uniqueWords.wordsReaderAndCounter();

        List<Map.Entry<String, Integer>> list = uniqueWords.sortByValue(wordMap);

        for (Map.Entry<String, Integer> entry : list) {

            System.out.println(entry.getKey() + " = " + entry.getValue());

        }

        long finish = System.currentTimeMillis();

        System.out.println("Total JVM memory after finish: " + Runtime.getRuntime().totalMemory());

        System.out.println("Max total JVM memory: " + MAX_TOTAL_MEMORY_USED);

        System.out.println("Final time: " + (finish - start) + " sec");

    }

}
