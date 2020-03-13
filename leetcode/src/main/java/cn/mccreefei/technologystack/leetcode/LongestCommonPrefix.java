package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2020-03-13 上午10:30
 * @refer <href>https://leetcode.com/problems/longest-common-prefix/</href>
 * @idea 遍历 依次记录共同的最长前缀
 */
public class LongestCommonPrefix {
    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        String result = strs[0];
        for (int i = 1; i < strs.length; i++) {
            int commonLength = 0;
            for (int j = 0; j < Math.min(result.length(), strs[i].length()); j++) {
                if (result.charAt(j) == strs[i].charAt(j)) {
                    commonLength++;
                } else {
                    break;
                }
            }
            if (commonLength == 0) {
                return "";
            }
            result = result.substring(0, commonLength);
        }
        return result;
    }
}
