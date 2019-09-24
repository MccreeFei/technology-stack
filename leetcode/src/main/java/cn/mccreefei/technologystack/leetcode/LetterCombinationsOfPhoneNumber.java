package cn.mccreefei.technologystack.leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author MccreeFei
 * @create 2019-09-24 下午7:14
 * @refer <href>https://leetcode.com/problems/letter-combinations-of-a-phone-number/</href>
 * @idea 使用队列
 */
public class LetterCombinationsOfPhoneNumber {
    public List<String> letterCombinations(String digits) {
        LinkedList<String> queue = new LinkedList<>();
        if (digits == null || digits.length() == 0) {
            return queue;
        }
        String[] mapping = {"0", "1", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
        queue.offer("");
        char[] digitArray = digits.toCharArray();
        for (int i = 0; i < digitArray.length; i++) {
            char[] chars = mapping[Character.getNumericValue(digitArray[i])].toCharArray();
            while (queue.peek().length() == i) {
                String poll = queue.poll();
                for (char c : chars) {
                    queue.offer(poll + c);
                }
            }
        }
        return queue;
    }


}
