package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2019-09-12 上午9:20
 * @refer <href>https://leetcode.com/problems/partition-equal-subset-sum/</href>
 * @idea 背包 题目转化为数组中是否能找到一些数使之总和等于sum/2 dp[i][j]代表前i个数是否能达到总和等于j
 * 转化方程 dp[i][j] = dp[i-1][j] || dp[i-1][j-nums[i-1]] 数的拿与不拿理解
 */
public class PartitionEqualSubsetSum {
    public boolean canPartition(int[] nums) {
        if (nums == null || nums.length < 2) {
            return false;
        }
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        //sum是奇数 false
        if ((sum & 1) == 1) {
            return false;
        }
        sum /= 2;
        // 是否前i个数可以总和达到j
        boolean[][] dp = new boolean[nums.length + 1][sum + 1];
        // 前i个数达到总和是0总是true
        for (int i = 0; i < nums.length + 1; i++) {
            dp[i][0] = true;
        }
        for (int i = 1; i < nums.length + 1; i++) {
            for (int j = 1; j < sum + 1; j++) {
                dp[i][j] = dp[i-1][j];
                if (j - nums[i-1] >= 0) {
                    dp[i][j] = dp[i][j] || dp[i-1][j-nums[i-1]];
                }
            }
        }
        return dp[nums.length][sum];
    }
}