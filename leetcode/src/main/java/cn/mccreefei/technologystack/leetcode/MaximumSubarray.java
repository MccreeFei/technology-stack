package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2019-09-26 下午7:53
 * @refer <href>https://leetcode.com/problems/maximum-subarray/</href>
 * @idea 动态规划 dp[i]代表以第i个数作为结尾的subarray中最大sum 转移方程 dp[i] = dp[i-1] > 0 ? dp[i-1]+nums[i-1] : nums[i-1]
 */
public class MaximumSubarray {
    public int maxSubArray(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int[] dp = new int[nums.length + 1];
        int max = Integer.MIN_VALUE;
        for (int i = 1; i <= nums.length; i++) {
            dp[i] = dp[i-1] > 0 ? dp[i-1] + nums[i-1] : nums[i-1];
            max = Math.max(dp[i], max);
        }
        return max;
    }
}
