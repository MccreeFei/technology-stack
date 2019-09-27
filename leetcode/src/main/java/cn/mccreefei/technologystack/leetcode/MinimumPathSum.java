package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2019-09-27 上午10:15
 * @refer <href>https://leetcode.com/problems/minimum-path-sum/</href>
 * @idea 动态规划 dp[i][j]代表到达grid[i][j]的最小sum,转移方程dp[i][j] = Math.min(dp[i-1][j], dp[i][j-1]) + grid[i][j]
 */
public class MinimumPathSum {
    public int minPathSum(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }
        int row = grid.length;
        int col = grid[0].length;
        int[][] dp = new int[row][col];
        dp[0][0] = grid[0][0];
        for (int i = 1; i < col; i++) {
            dp[0][i] = dp[0][i-1] + grid[0][i];
        }

        for (int i = 1; i < row; i++) {
            dp[i][0] = dp[i-1][0] + grid[i][0];
        }

        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                dp[i][j] = Math.min(dp[i-1][j], dp[i][j-1]) + grid[i][j];
            }
        }
        return dp[row-1][col-1];
    }

}
