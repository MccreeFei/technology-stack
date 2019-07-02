package cn.mccreefei.technologystack.leetcode;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author MccreeFei
 * @create 2019-07-02 上午11:36
 * <href>https://leetcode.com/problems/longest-substring-without-repeating-characters/</href>
 * <idea>滑动窗口 right不停右移 直到窗口中有重复元素 删除left至重复元素  整个过程记录滑动窗口的最大值</idea>
 */
public class LongestSubstringWithoutRepeatingCharacters {
    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.equals("")) {
            return 0;
        }
        Set<Character> set = new HashSet<>();
        int left = 0;
        int right = 0;
        int maxSize = 0;
        char[] chars = s.toCharArray();
        for (right = 0; right < s.length(); right++) {
            if (!set.contains(chars[right])) {
                set.add(chars[right]);
                int windowSize = right - left + 1;
                maxSize = maxSize > windowSize ? maxSize : windowSize;
            } else {
                while (chars[left] != chars[right]) {
                    set.remove(chars[left++]);
                }
                left++;
            }
        }
        return maxSize;
    }
}
