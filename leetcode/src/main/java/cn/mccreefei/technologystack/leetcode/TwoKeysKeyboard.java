package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2020-01-07 上午10:48
 * @refer <href>https://leetcode.com/problems/2-keys-keyboard/</href>
 * @idea 动态规划
 */
public class TwoKeysKeyboard {
    public int minSteps(int n) {
        int[] dp = new int[n + 1];
        for (int i = 2; i <= n; i++) {
            for (int j = i - 1; j > 0; j--) {
                if (i % j == 0) {
                    dp[i] = dp[j] + i / j;
                    break;
                }
            }
        }
        return dp[n];
    }
}
