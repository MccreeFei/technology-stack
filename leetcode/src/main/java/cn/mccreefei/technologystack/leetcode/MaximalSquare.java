package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2019-08-29 下午5:19
 * @refer <href>https://leetcode.com/problems/maximal-square/</href>
 * @idea 动态规划 dp[i][j]代表 (i,j)为右下角组成的正方形的边长 matrix[i-1][j-1]==‘0’构不成正方形因此dp[i][j]=0
 * matrix[i-1][j-1]=='1' 则取决于dp[i-1][j],dp[i][j-1],dp[i-1][j-1]的最小边长+1
 */
public class MaximalSquare {
    public int maximalSquare(char[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return 0;
        }
        int row = matrix.length;
        int col = matrix[0].length;
        int max = 0;
        int[][] dp = new int[row+1][col+1];

        for (int i = 1; i < row + 1; i++) {
            for (int j = 1; j < col + 1; j++) {
                if (matrix[i-1][j-1] == '1') {
                    dp[i][j] = Math.min(Math.min(dp[i-1][j-1], dp[i-1][j]), dp[i][j-1]) + 1;
                    max = Math.max(max, dp[i][j]);
                }
            }
        }
        return max * max;
    }

}
