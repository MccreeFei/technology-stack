package cn.mccreefei.technologystack.leetcode;

import java.util.Arrays;

/**
 * @author MccreeFei
 * @create 2019-09-04 上午9:43
 * @refer <href>https://leetcode.com/problems/perfect-squares/</href>
 * @idea 动态规划 转移方程：
 * for (int j = 1; j * j <= i; j++) {
 *                 dp[i] = Math.min(dp[i], dp[i-j*j] + 1);
 *             }
 */
public class PerfectSquares {
    public int numSquares(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 0;
        Arrays.fill(dp, 1, n + 1, Integer.MAX_VALUE);
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j * j <= i; j++) {
                dp[i] = Math.min(dp[i], dp[i-j*j] + 1);
            }
        }
        return dp[n];
    }
}
