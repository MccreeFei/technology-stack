package cn.mccreefei.technologystack.leetcode;

import java.util.List;

/**
 * @author MccreeFei
 * @create 2019-08-09 上午10:13
 * @refer <href>https://leetcode.com/problems/word-break/</href>
 * @idea 动态规划 dp[i] 表示前i个字符是否有字典匹配 dp[i+word.length()-1] = dp[i-1] && word.equals(SUBSTR)
 */
public class WordBreak {
    public boolean wordBreak(String s, List<String> wordDict) {
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        for (int i = 1; i < dp.length; i++) {
            for (String word : wordDict) {
                if (dp[i-1] && i + word.length() <= dp.length && word.equals(s.substring(i-1, i-1+word.length()))) {
                    dp[i + word.length() -1] = true;
                }
            }
        }
        return dp[dp.length-1];
    }
}
