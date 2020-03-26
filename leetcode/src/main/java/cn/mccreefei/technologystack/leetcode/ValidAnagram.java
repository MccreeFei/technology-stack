package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2020-03-26 下午5:44
 * @refer <href>https://leetcode.com/problems/valid-anagram/</href>
 * @idea count数组记录字母出现次数
 */
public class ValidAnagram {
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        int[] count = new int[26];
        for (int i = 0; i < s.length(); i++) {
            count[s.charAt(i) - 'a']++;
        }
        for (int i = 0; i < t.length(); i++) {
            if ((count[t.charAt(i) - 'a']--) == 0) {
                return false;
            }
        }
        return true;
    }
}
