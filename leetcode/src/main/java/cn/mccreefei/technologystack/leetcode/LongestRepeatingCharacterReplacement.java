package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2019-11-27 上午10:55
 * @refer <href>https://leetcode.com/problems/longest-repeating-character-replacement/</href>
 * @idea 滑动窗口 记录窗口中出现最多数量maxCount 当窗口长度 > maxCount + k时 窗口左边界右移
 *       maxCount只是曾今出现的最大数量 基于我们所求最大长度 并不影响结果
 */
public class LongestRepeatingCharacterReplacement {
    public int characterReplacement(String s, int k) {
        int[] data = new int[26];
        int start = 0;
        int end = 0;
        int maxCount = 0;
        int maxLength = 0;
        for (; end < s.length(); end++) {
            int index = s.charAt(end) - 'A';
            maxCount = Math.max(maxCount, ++data[index]);
            if (end - start + 1 > maxCount + k) {
                data[s.charAt(start++)-'A']--;
            }
            maxLength = Math.max(maxLength, end - start + 1);
        }
        return maxLength;
    }
}
