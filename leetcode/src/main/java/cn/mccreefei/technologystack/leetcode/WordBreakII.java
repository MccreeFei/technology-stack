package cn.mccreefei.technologystack.leetcode;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author MccreeFei
 * @create 2020-07-31 下午1:55
 * @refer <href>https://leetcode.com/problems/word-break-ii/</href>
 * @idea dfs
 */
public class WordBreakII {
    public List<String> wordBreak(String s, List<String> wordDict) {
        Map<String, List<String>> map = new HashMap<>();
        List<String> result = dfs(s, wordDict,  map);
        return result;
    }

    private List<String> dfs(String s, List<String> wordDict, Map<String, List<String>> map) {
        if (map.containsKey(s)) {
            return map.get(s);
        }
        List<String> res = new LinkedList<>();
        for (String word : wordDict) {
            if (s.startsWith(word)) {
                String next = s.substring(word.length());
                if (next.length() == 0) {
                    res.add(word);
                } else {
                    for (String sub : dfs(next, wordDict, map)) {
                        res.add(word + " " + sub);
                    }
                }
            }
        }
        map.put(s, res);
        return res;
    }
}
