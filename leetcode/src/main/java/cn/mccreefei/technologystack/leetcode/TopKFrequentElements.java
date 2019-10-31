package cn.mccreefei.technologystack.leetcode;

import java.util.*;

/**
 * @author MccreeFei
 * @create 2019-10-31 下午2:43
 * @refer <href>https://leetcode.com/problems/top-k-frequent-elements/</href>
 * @idea map记录num -> freq, buckets[] 记录freq -> 对应num的列表, 选取最大k个freq的num
 */
public class TopKFrequentElements {
    /**
     * bucket数组实现
     */
    public List<Integer> topKFrequent(int[] nums, int k) {
        List<Integer> result = new ArrayList<>();
        Map<Integer, Integer> numFreqMap = new HashMap<>();
        for (int num : nums) {
            numFreqMap.put(num, numFreqMap.getOrDefault(num, 0) + 1);
        }
        ArrayList[] buckets = new ArrayList[nums.length];
        for (Integer key : numFreqMap.keySet()) {
            Integer freq = numFreqMap.get(key);
            if (buckets[freq-1] == null) {
                buckets[freq-1] = new ArrayList();
            }
            buckets[freq-1].add(key);
        }

        for (int i = nums.length - 1; i >= 0 && k > 0; i--) {
            if (buckets[i] != null) {
                result.addAll(buckets[i]);
                k -= buckets[i].size();
            }
        }
        return result;
    }


    /**
     * 最大堆实现
     */
    public List<Integer> topKFrequentSolution2(int[] nums, int k) {
        List<Integer> result = new ArrayList<>();
        Map<Integer, Integer> numFreqMap = new HashMap<>();
        for (int num : nums) {
            numFreqMap.put(num, numFreqMap.getOrDefault(num, 0) + 1);
        }
        PriorityQueue<Map.Entry<Integer, Integer>> maxHeap = new PriorityQueue<>(
                (a ,b) -> b.getValue() - a.getValue());
        maxHeap.addAll(numFreqMap.entrySet());

        while (result.size() < k) {
            Map.Entry<Integer, Integer> poll = maxHeap.poll();
            result.add(poll.getKey());
        }
        return result;

    }


}
