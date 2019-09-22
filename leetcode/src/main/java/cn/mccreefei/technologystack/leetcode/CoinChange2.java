package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2019-09-22 上午10:20
 * @refer <href>https://leetcode.com/problems/coin-change-2/</href>
 * @idea 状态转移方程 dp[i][j]表示前i种coin能到达amount有多少种方式
 *       dp[i][j] == dp[i-1][j] + dp[i][j-coins[i-1]]
 *       即不使用当前coin达到amount的方式数 + 使用当前coin达到的amount方式数
 */
public class CoinChange2 {
    public int change(int amount, int[] coins) {
        int[][] dp = new int[coins.length+1][amount+1];
        for (int i = 0; i <= coins.length; i++) {
            dp[i][0] = 1;
        }
        for (int i = 1; i <= coins.length; i++) {
            for (int j = 1; j <= amount; j++) {
                //不使用当前coin
                dp[i][j] = dp[i-1][j];
                //使用当前coin
                if (j >= coins[i-1]) {
                    dp[i][j] += dp[i][j-coins[i-1]];
                }
            }
        }
        return dp[coins.length][amount];
    }
}
