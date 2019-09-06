package cn.mccreefei.technologystack.leetcode;

import java.util.Arrays;

/**
 * @author MccreeFei
 * @create 2019-09-06 上午11:39
 * @refer <href>https://leetcode.com/problems/coin-change/</href>
 * @idea 动态规划 状态转移方程 dp[i] = Math.min(dp[i-coin] + 1, dp[i])  注意初始赋值MAX_VALUE-1,这样dp[i-coin]+1不会溢出
 */
public class CoinChange {
    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, Integer.MAX_VALUE - 1);
        dp[0] = 0;

        for (int i = 1; i <= amount; i++) {
            for (int coin : coins) {
                if (i - coin >= 0) {
                    dp[i] = Math.min(dp[i-coin] + 1, dp[i]);
                }
            }
        }
        return dp[amount] >= Integer.MAX_VALUE - 1 ? -1 : dp[amount];
    }
}
