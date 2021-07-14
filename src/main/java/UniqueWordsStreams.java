import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


class UniqueWordsStreams {

    private static final String WORD_FILE = "src/main/resources/mlnwords.txt";


    public static void main(String[] args) {

        Map<String, Integer> wordMap = wordsReaderAndCounter();

        List<Map.Entry<String, Integer>> list = sortByValue(wordMap);

        for (Map.Entry<String, Integer> entry : list) {

            System.out.println(entry.getKey() + " = " + entry.getValue());
        }
    }


    private static Map<String, Integer> wordsReaderAndCounter() {

        Map<String, Integer> wordsMap = new HashMap<>();

        try {

            Files.lines(Paths.get(WORD_FILE))

                    .map(line -> new StringTokenizer(line , "\",.; "))

                    .forEach(stringTokenizer -> {

                        while (stringTokenizer.hasMoreTokens()) {

                            String tmp = stringTokenizer.nextToken().toLowerCase();

                            if (wordsMap.containsKey(tmp)) {

                                wordsMap.put(tmp , wordsMap.get(tmp) + 1);

                            } else {

                                wordsMap.put(tmp , 1);
                            }
                        }

                    });

        } catch (Exception e) {

            e.printStackTrace();

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






