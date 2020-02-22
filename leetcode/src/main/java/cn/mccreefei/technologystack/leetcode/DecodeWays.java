package cn.mccreefei.technologystack.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MccreeFei
 * @create 2020-02-22 上午11:19
 * @refer <href>https://leetcode.com/problems/decode-ways/</href>
 * @idea dp dp[i] = (dp[i-1] + dp[i-2])(符合条件)
 */
public class DecodeWays {
    public int numDecodings(String s) {
        char[] chars = s.toCharArray();
        int[] dp = new int[chars.length + 1];
        dp[0] = 1;
        for (int i = 1; i < dp.length; i++) {
            dp[i] = chars[i - 1] == '0' ? 0 : dp[i - 1];
            if (i > 1 && chars[i - 2] != '0') {
                int val = Character.getNumericValue(chars[i - 2]) * 10 +
                        Character.getNumericValue(chars[i - 1]);
                dp[i] += (val > 0 && val <= 26) ? dp[i - 2] : 0;
            }
            if (dp[i] == 0) {
                return 0;
            }

        }
        return dp[dp.length - 1];
    }


}
