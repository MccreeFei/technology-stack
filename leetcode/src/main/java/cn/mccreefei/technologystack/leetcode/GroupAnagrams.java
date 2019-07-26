package cn.mccreefei.technologystack.leetcode;

import java.util.*;

/**
 * @author MccreeFei
 * @create 2019-07-26 下午2:52
 * @refer <href>https://leetcode.com/problems/group-anagrams/</href>
 * @idea 使用count数组表示一个str各个字符出现多少次, 然后利用map分组
 */
public class GroupAnagrams {
    public List<List<String>> groupAnagrams(String[] strs) {
        List<List<String>> result = new ArrayList<>();
        int aIndex = (int)'a';
        Map<String, List<String>> map = new HashMap<>();
        for (int i = 0; i < strs.length; i++) {
            int[] counts = new int[26];
            char[] chars = strs[i].toCharArray();
            for (int j = 0; j < chars.length; j++) {
                counts[(int)chars[j] - aIndex]++;
            }
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < counts.length; j++) {
                sb.append(j).append(counts[j]);
            }
            String key = sb.toString();
            List<String> value = map.computeIfAbsent(key, k -> new ArrayList<>());
            value.add(strs[i]);
        }
        result.addAll(map.values());
        return result;
    }

}
