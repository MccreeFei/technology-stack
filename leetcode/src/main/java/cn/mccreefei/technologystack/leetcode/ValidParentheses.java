package cn.mccreefei.technologystack.leetcode;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Objects;

/**
 * @author MccreeFei
 * @create 2019-06-11 上午10:06
 * @refer <href>https://leetcode.com/problems/valid-parentheses/</href>
 * @idea 栈
 */
public class ValidParentheses {
    private Deque<Character> stack = new LinkedList<Character>();
    public boolean isValid(String s) {
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(' || c == '[' || c == '{') {
                stack.offerLast(c);
                continue;
            }
            if (c == ')' || c == ']' || c == '}') {
                Character last = stack.pollLast();
                if (Objects.isNull(last) || !checkMatch(c, last)) {
                    return false;
                }

            }
        }
        return stack.isEmpty();
    }

    private boolean checkMatch(char right, char left) {
        if (right == ')') {
            return left == '(';
        } else if (right == ']') {
            return left == '[';
        } else if (right == '}') {
            return left == '{';
        }
        return false;
    }

}
