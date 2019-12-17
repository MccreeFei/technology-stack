package cn.mccreefei.technologystack.leetcode;

import java.util.*;

/**
 * @author MccreeFei
 * @create 2019-12-16 下午7:28
 * @refer <href>https://leetcode.com/problems/top-k-frequent-words/</href>
 * @idea 最小堆
 */
public class TopKFrequentWords {

    public List<String> topKFrequent(String[] words, int k) {
        List<String> result = new LinkedList<>();
        PriorityQueue<Map.Entry<String, Integer>> minHeap = new PriorityQueue<>(k, (a ,b) ->
                a.getValue().equals(b.getValue()) ?
                        b.getKey().compareTo(a.getKey()) : a.getValue() - b.getValue());
        Map<String, Integer> map = new HashMap<>();
        for (String word : words) {
            map.put(word, map.getOrDefault(word, 0) + 1);
        }
        map.entrySet().forEach(e -> {
            minHeap.offer(e);
            if (minHeap.size() > k) {
                minHeap.poll();
            }
        });
        while (!minHeap.isEmpty()) {
            result.add(0, minHeap.poll().getKey());
        }
        return result;
    }
}
