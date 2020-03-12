package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2020-03-12 下午3:11
 * @refer <href>https://leetcode.com/problems/longest-substring-with-at-least-k-repeating-characters/</href>
 * @idea 滑动窗口 same idea as MinimumWindowSubstring
 */
public class LongestSubstringWithAtLeastKRepeatingCharacters {
    public int longestSubstring(String s, int k) {
        int max = 0;
        for (int i = 1; i <= 26; i++) {
            max = Math.max(help(s, k, i), max);
        }
        return max;
    }

    private int help(String s, int k, int targetUniqueNum) {
        int[] count = new int[26];
        int uniqueNum = 0, notLessThanKNum = 0;
        int start = 0, end = 0;
        int maxLength = 0;
        while (end < s.length()) {
            int c = count[s.charAt(end) - 'a'];
            if (c == 0) {
                uniqueNum++;
            }
            if (c == k - 1) {
                notLessThanKNum++;
            }
            count[s.charAt(end++) - 'a']++;
            while (uniqueNum > targetUniqueNum) {
                c = count[s.charAt(start) - 'a'];
                if (c == 1) {
                    uniqueNum--;
                }
                if (c == k) {
                    notLessThanKNum--;
                }
                count[s.charAt(start++) - 'a']--;
            }
            if (uniqueNum == targetUniqueNum && uniqueNum == notLessThanKNum) {
                maxLength = Math.max(maxLength, end - start);
            }

        }
        return maxLength;
    }

}
