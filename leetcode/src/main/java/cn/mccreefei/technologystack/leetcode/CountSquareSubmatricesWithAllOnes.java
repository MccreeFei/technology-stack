package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2020-05-21 下午5:08
 * @refer <href>https://leetcode.com/problems/count-square-submatrices-with-all-ones/</href>
 * @idea dp[i][j]以i,j为右底部构成的正方形的个数也就是最大正方形边长
 * dp[i][j] = Math.min(dp[i-1][j], dp[i][j-1], dp[i-1][j-1]) + 1
 */
public class CountSquareSubmatricesWithAllOnes {
    public int countSquares(int[][] matrix) {
        int row = matrix.length;
        int col = matrix[0].length;
        int[][] dp = new int[row][col];
        int res = 0;
        for (int i = 0; i < row; i++) {
            dp[i][0] = matrix[i][0];
        }
        for (int j = 0; j < col; j++) {
            dp[0][j] = matrix[0][j];
        }
        for (int i = 0 ; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (i > 0 && j > 0 && matrix[i][j] == 1) {
                    dp[i][j] = Math.min(Math.min(dp[i-1][j], dp[i][j-1]), dp[i-1][j-1]) + 1;
                }
                res += dp[i][j];
            }
        }
        return res;
    }
}
