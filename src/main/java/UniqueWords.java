import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

public class UniqueWords {

    private static final String WORD_FILE = "C:\\Users\\Yurko\\Desktop\\text.txt";


    public static void main(String[] args) {

        Map<String, Integer> wordMap = wordsReaderAndCounter();

        List<Map.Entry<String, Integer>> list = sortByValue(wordMap);

        for (Map.Entry<String, Integer> entry : list) {

            System.out.println(entry.getKey() + " = " + entry.getValue());
        }
    }


    private static Map<String, Integer> wordsReaderAndCounter() {

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

                StringTokenizer st = new StringTokenizer(line , "\",.; ");

                while (st.hasMoreTokens()) {

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

    private static List<Map.Entry<String, Integer>> sortByValue(Map<String, Integer> mapForSorting) {

        Set<Map.Entry<String, Integer>> set = mapForSorting.entrySet();

        List<Map.Entry<String, Integer>> list = new ArrayList<>(set);

        list.sort((o1 , o2) -> (o2.getValue()).compareTo(o1.getValue()));

        return list;
    }

}



