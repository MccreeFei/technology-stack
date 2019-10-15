package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2019-10-15 下午6:37
 * @refer <href>https://leetcode.com/problems/unique-binary-search-trees/</href>
 * @idea G(n) = F(1,n) + F(2,n) + ...F(n,n) = G(0)*G(n-1) + G(1)*G(n-2) + ...G(n-1)*G(0)
 *  G(n)表示n个数字可以组成多少个二叉搜索树 F(i,j)表示以i为root,j个数字可以组成多少二叉搜索树
 */
public class UniqueBinarySearchTrees {
    public int numTrees(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                dp[i] += dp[j] * dp[i-j-1];
            }
        }
        return dp[n];
    }
}
