package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2020-05-18 下午4:15
 * @refer <href>https://leetcode.com/problems/permutation-in-string/</href>
 * @idea 滑动窗口 hash数组记录s1各字符的个数 确保窗口大小不大于s1的长度期间维护hash数组 记录窗口中和s1等同的字符个数
 *  如果出现了count==s1.length则返回true
 */
public class PermutationInString {
    public boolean checkInclusion(String s1, String s2) {
        int[] hash = new int[26];
        for (int i = 0; i < s1.length(); i++) {
            hash[s1.charAt(i) - 'a']++;
        }
        int start = 0, end = 0, count = 0;
        while (end < s2.length()) {
            if (hash[s2.charAt(end) - 'a'] -- > 0) {
                count++;
            }
            if (count == s1.length()) {
                return true;
            }
            if (end - start + 1 == s1.length()) {
                if (hash[s2.charAt(start++) - 'a'] ++ >= 0) {
                    count--;
                }
            }
            end++;
        }
        return false;
    }

}
