package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2020-05-15 下午4:21
 * @refer <href>https://leetcode.com/problems/maximum-sum-circular-subarray/</href>
 * @idea 两种情况 一种最大子序列不出现在循环中 另一种出现在循环中此时转化成totalSum - Min SubArray
 * 另外考虑全是负数情况 totalSum == Min SubArray
 *
 */
public class MaximumSumCircularSubarray {
    public int maxSubarraySumCircular(int[] A) {
        int totalSum = 0, max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;
        int curMax = 0,curMin = 0;
        for (int a : A) {
            totalSum += a;
            //包含当前a的最大sum
            curMax = Math.max(a, curMax + a);
            max = Math.max(max, curMax);
            //包含当前a的最小sum
            curMin = Math.min(curMin + a, a);
            min = Math.min(curMin, min);
        }
        return totalSum == min ? max : Math.max(max, totalSum - min);
    }
}
