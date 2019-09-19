package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2019-09-19 下午4:33
 * @refer <href>https://leetcode.com/problems/target-sum/</href>
 * @idea 转化为找出有多少种组合，使得每个组合的加值 == (S + sum)/2, 转移方程dp[i][j] = dp[i-1][j] + dp[i-1][j-nums[i]]
 *       代表前i个数有多少种组合使得加值等于j
 */
public class TargetSum {
    public int findTargetSumWays(int[] nums, int S) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        if (sum < S || (S + sum) % 2 == 1) {
            return 0;
        }
        int pSum = (S + sum) / 2;
        int[][] dp = new int[nums.length + 1][pSum + 1];
        for (int i = 0; i <= nums.length; i++) {
            dp[i][0] = 1;
        }
        for (int i = 1; i <= nums.length; i++) {
            for (int j = 0; j <= pSum; j++) {
                dp[i][j] = dp[i-1][j];
                if (j >= nums[i-1]) {
                    dp[i][j] += dp[i-1][j - nums[i-1]];
                }
            }
        }
        return dp[nums.length][pSum];
    }

}
