package cn.mccreefei.technologystack.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author MccreeFei
 * @create 2019-11-28 上午11:19
 * @refer <href>https://leetcode.com/problems/longest-consecutive-sequence/</href>
 * @idea map存放左右边界的num -> 序列长度 维护每一个边界值 已经存在的值说明重复直接进行下一次循环
 */
public class LongestConsecutiveSequence {
    public int longestConsecutive(int[] nums) {
        /*
        序列左右边界num -> 序列长度
         */
        Map<Integer, Integer> boundCountMap = new HashMap<>();
        int maxSequenceLen = 0;
        for (int num : nums) {
            if (boundCountMap.containsKey(num)) {
                continue;
            }
            int left = boundCountMap.getOrDefault(num - 1, 0);
            int right = boundCountMap.getOrDefault(num + 1, 0);
            int newSequenceLen = left + right + 1;
            maxSequenceLen = Math.max(maxSequenceLen, newSequenceLen);
            boundCountMap.put(num, newSequenceLen);
            if (left != 0) {
                boundCountMap.put(num - left, newSequenceLen);
            }
            if (right != 0) {
                boundCountMap.put(num + right, newSequenceLen);
            }
        }
        return maxSequenceLen;
    }

}
