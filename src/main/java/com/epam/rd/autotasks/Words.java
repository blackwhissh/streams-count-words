package com.epam.rd.autotasks;



import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


public class Words {

    public static String countWords(List<String> lines) {


        List<String> filtered = lines
                .stream()
//                .map(line -> line.split("[^a-zA-Z0-9]+"))
                .map(line -> line.replace('.', ' ')
                        .replace(',', ' ')
                        .replace('!', ' ')
                        .replace('?', ' ')
                        .replace('"', ' ')
                        .replace('“', ' ')
                        .replace('’', ' ')
                        .replace(':', ' ')
                        .replace('—', ' ')
                        .replace('”', ' ')
                        .replace('(', ' ')
                        .replace(')', ' ')
                        .replace(';', ' ')
                        .replace('‘', ' ')
                        .replace('-', ' ')
                        .replace('*', ' ')
                        .replace('\'', ' ')
                        .replace('/', ' ')
                        .split(" "))
                .flatMap(Arrays::stream)
                .filter(word -> word.length() >= 4)
                .map(String::toLowerCase)
                .collect(Collectors.toList());


        Map<String, Integer> collect = filtered
                .stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.summingInt(e -> 1)));

        Map<String, Integer> lengthFilter = collect
                .entrySet().stream()
                .filter(key -> collect.get(key.getKey()) >= 10)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        Comparator<Map.Entry<String, Integer>> comparator = Map.Entry.comparingByValue();

        Map<String, Integer> sorted =
                lengthFilter
                        .entrySet().stream()
                        .sorted(comparator.reversed().thenComparing(Map.Entry.comparingByKey()))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));


        StringBuilder result = new StringBuilder();
        sorted.forEach((s, integer) -> result.append(s).append(" - ").append(integer).append("\n"));

        int last = result.lastIndexOf("\n");
        result.deleteCharAt(last);

        return result.toString();
    }

}
