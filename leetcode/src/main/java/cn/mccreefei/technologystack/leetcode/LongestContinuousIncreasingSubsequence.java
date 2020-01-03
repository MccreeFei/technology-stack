package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2020-01-03 上午11:11
 * @refer <href>https://leetcode.com/problems/longest-continuous-increasing-subsequence/</href>
 * @idea 记录prevMax：连接之前序列的最大长度, 期间记录最大长度
 */
public class LongestContinuousIncreasingSubsequence {
    public int findLengthOfLCIS(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int max = 1;
        int prevMax = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > nums[i-1]) {
                prevMax++;
                max = Math.max(max, prevMax);
            } else {
                prevMax = 1;
            }
        }
        return max;

    }
}
