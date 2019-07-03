package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2019-07-03 下午5:53
 * @refer <href>https://leetcode.com/problems/longest-palindromic-substring/</href>
 * @idea 动态规划 一个字符串是回文，左右加上同一个字符那么依旧是回文。状态转移方程dp[i][j] = (s[i]==s[j] && dp[i+1][j-1])
 * dp[i][j] 代表从i至j位置（包含ij）组成的字符串是否回文
 */
public class LongestPalindromicSubstring {
    public String longestPalindrome(String s) {
        if (s == null || s.length() == 0 || s.length()== 1) {
            return s;
        }
        int maxLength = 0;
        int resI = 0;
        int resJ = 0;
        boolean[][] dp = new boolean[s.length()][s.length()];
        for (int i = s.length()-1; i >= 0; i--) {
            for (int j = i; j < s.length(); j++) {
                if (i == j) {
                    dp[i][j] = true;
                } else if (j - i == 1) {
                    dp[i][j] = s.charAt(i) == s.charAt(j);
                }else {
                    dp[i][j] = s.charAt(i) == s.charAt(j) && dp[i+1][j-1];
                }
                if (dp[i][j] && (j-i+1) > maxLength) {
                    maxLength = j-i+1;
                    resI = i;
                    resJ = j;
                }
            }
        }
        return s.substring(resI, resJ+1);
    }
}
