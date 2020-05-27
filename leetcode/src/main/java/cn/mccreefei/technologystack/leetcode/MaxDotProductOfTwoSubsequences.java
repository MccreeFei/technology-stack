package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2020-05-27 上午11:42
 * @refer <href>https://leetcode.com/problems/max-dot-product-of-two-subsequences/</href>
 * @idea dp
 */
public class MaxDotProductOfTwoSubsequences {
    public int maxDotProduct(int[] nums1, int[] nums2) {
        int[][] dp = new int[nums1.length][nums2.length];
        for (int i = 0; i < nums1.length; i++) {
            for (int j = 0; j < nums2.length; j++) {
                dp[i][j] = nums1[i] * nums2[j];
                if (i > 0 && j > 0) {
                    dp[i][j] += Math.max(dp[i-1][j-1], 0);
                }
                if (i > 0) {
                    dp[i][j] = Math.max(dp[i][j], dp[i-1][j]);
                }
                if (j > 0) {
                    dp[i][j] = Math.max(dp[i][j], dp[i][j-1]);
                }
            }
        }
        return dp[nums1.length - 1][nums2.length - 1];
    }
}
