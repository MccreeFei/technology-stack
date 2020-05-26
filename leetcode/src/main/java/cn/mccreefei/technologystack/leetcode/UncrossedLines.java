package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2020-05-26 上午10:38
 * @refer <href>https://leetcode.com/problems/uncrossed-lines/</href>
 * @idea dp[i][j] 以i为A的终节点和j为B的终节点所能组成的最多连线
 *  if (A[i][j]==B[i][j]) dp[i][j] = dp[i-1][j-1] + 1 else dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1])
 */
public class UncrossedLines {
    public int maxUncrossedLines(int[] A, int[] B) {
        int[][] dp = new int[A.length+1][B.length+1];
        for (int i = 1; i <= A.length; i++) {
            for (int j = 1; j <= B.length; j++) {
                if (A[i-1] == B[j-1]) {
                    dp[i][j] = dp[i-1][j-1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]);
                }
            }
        }
        return dp[A.length][B.length];
    }
}
