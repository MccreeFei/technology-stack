package cn.mccreefei.technologystack.leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * @author MccreeFei
 * @create 2020-05-22 下午3:56
 * @refer <href>https://leetcode.com/problems/sort-characters-by-frequency/</href>
 * @idea 最大堆
 */
public class SortCharactersByFrequency {
    public String frequencySort(String s) {
        Map<Character, Integer> countMap = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            countMap.put(s.charAt(i), countMap.getOrDefault(s.charAt(i), 0) + 1);
        }
        PriorityQueue<Map.Entry<Character, Integer>> queue = new PriorityQueue<>(
                (a, b) -> b.getValue() - a.getValue());
        queue.addAll(countMap.entrySet());
        StringBuilder sb = new StringBuilder();
        while (!queue.isEmpty()) {
            Map.Entry<Character, Integer> e = queue.poll();
            for (int i = 0; i < e.getValue(); i++) {
                sb.append(e.getKey());
            }
        }
        return sb.toString();
    }
}
