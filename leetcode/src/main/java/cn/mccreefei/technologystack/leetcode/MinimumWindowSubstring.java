package cn.mccreefei.technologystack.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author MccreeFei
 * @create 2020-03-12 上午11:36
 * @refer <href>https://leetcode.com/problems/minimum-window-substring/</href>
 * @idea 滑动窗口 substring常规解模板 <href>https://leetcode.com/problems/minimum-window-substring/discuss/26808/here-is-a-10-line-template-that-can-solve-most-substring-problems</href>
 * substring常规解思路：
 * 1.start=0, end=0;
 * map[char]
 * while (end < s.length()) {
 *     记录map
 *     while (valid) {
 *         至达到平衡时，记录平衡值
 *         start移动至非平衡状态
 *         维护map
 *     }
 * }
 * 根据各个平衡状态的值，得出最优值
 */
public class MinimumWindowSubstring {
    public String minWindow(String s, String t) {
        int zero = 0;
        char zeroChar = (char) zero;
        int[] count = new int[128];
        for (int i = 0; i < t.length(); i++) {
            count[t.charAt(i) - zeroChar]++;
        }
        int totalCount = t.length();

        int start = 0, end = 0;
        int minStart = 0, minLength = Integer.MAX_VALUE;
        while (end < s.length()) {
            int c = count[s.charAt(end) - zeroChar];
            if (c > 0) {
                totalCount--;
            }
            count[s.charAt(end++) - zeroChar]--;
            while (totalCount == 0) {
                if (end - start < minLength) {
                    minLength = end - start;
                    minStart = start;
                }
                c = count[s.charAt(start) - zeroChar];
                if (c == 0) {
                    totalCount++;
                }
                count[s.charAt(start++) - zeroChar]++;
            }
        }
        return minLength == Integer.MAX_VALUE ? "" : s.substring(minStart, minStart + minLength);
    }

}
