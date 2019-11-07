package cn.mccreefei.technologystack.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MccreeFei
 * @create 2019-11-07 下午2:07
 * @refer <href>https://leetcode.com/problems/find-all-anagrams-in-a-string/</href>
 * @idea 滑动窗口
 */
public class FindAllAnagramsInString {
    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> result = new ArrayList<>();
        if (s == null || s.length() < 1 || p == null || p.length() < 1) {
            return result;
        }
        int start = 0, end = 0;
        int count = 0;
        int[] hash = new int[26];
        for (int i = 0; i < p.length(); i++) {
            hash[p.charAt(i)-'a'] ++;
        }
        while (end < s.length()) {
           if (hash[s.charAt(end)-'a'] > 0) {
               count++;
           }
           hash[s.charAt(end)-'a']--;
           if (count == p.length()) {
               result.add(start);
           }
           if (end - start + 1 == p.length()) {
               if (hash[s.charAt(start)-'a'] >= 0) {
                   count--;
               }
               hash[s.charAt(start) - 'a']++;
               start++;
           }
           end++;
        }
        return result;
    }
}
