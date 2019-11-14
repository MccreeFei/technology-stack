package cn.mccreefei.technologystack.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author MccreeFei
 * @create 2019-11-14 下午5:14
 * @refer <href>https://leetcode.com/problems/subarray-sum-equals-k/</href>
 * @idea sum[0, i-1] + sum[i, j] == sum[0, j]
 *       sum[0, i-1] = sum[0, j] - sum[i, j] = sum - k 即找出sum[0, i-1]出现数量
 */
public class SubarraySumEqualsK {
    public int subarraySum(int[] nums, int k) {
        /*
        prevSum -> count 曾经出现的sum -> 出现次数
         */
        Map<Integer, Integer> prevSumCountMap = new HashMap<>();
        int sum = 0;
        int result = 0;
        prevSumCountMap.put(0, 1);
        for (int num : nums) {
            sum += num;
            result += prevSumCountMap.getOrDefault(sum - k, 0);
            prevSumCountMap.put(sum, prevSumCountMap.getOrDefault(sum, 0) + 1);
        }
        return result;
    }
}
