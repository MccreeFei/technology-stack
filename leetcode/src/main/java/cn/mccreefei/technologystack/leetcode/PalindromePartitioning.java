package cn.mccreefei.technologystack.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MccreeFei
 * @create 2019-08-08 上午11:29
 * @refer <href>https://leetcode.com/problems/palindrome-partitioning/</href>
 * @idea 回溯法
 */
public class PalindromePartitioning {
    public List<List<String>> partition(String s) {
        List<List<String>> result = new ArrayList<>();
        char[] chars = s.toCharArray();
        dfs(result, new ArrayList<>(), chars, 0);
        return result;
    }

    private void dfs(List<List<String>> result, List<String> lst,
                     char[] chars, int start) {
       if (start > chars.length-1) {
           result.add(new ArrayList<>(lst));
       }

        for (int i = start; i < chars.length; i++) {
            if (!isPalindrome(chars, start, i)) {
                continue;
            }
            lst.add(String.valueOf(chars, start, i - start + 1));
            dfs(result, lst, chars, i+1);
            lst.remove(lst.size()-1);
        }
    }

    /**
     * 判断是否回文
     */
    private boolean isPalindrome(char[] chars, int start, int end) {
        while (end > start) {
            if (chars[start] != chars[end]) {
                return false;
            }
            end--;
            start++;
        }
        return true;
    }
}
