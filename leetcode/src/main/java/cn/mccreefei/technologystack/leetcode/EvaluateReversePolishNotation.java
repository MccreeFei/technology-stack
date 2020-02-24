package cn.mccreefei.technologystack.leetcode;

import java.util.LinkedList;

/**
 * @author MccreeFei
 * @create 2020-02-24 上午10:35
 * @refer <href>https://leetcode.com/problems/evaluate-reverse-polish-notation/</href>
 * @idea 栈
 */
public class EvaluateReversePolishNotation {
    public int evalRPN(String[] tokens) {
        LinkedList<Integer> stack = new LinkedList<Integer>();
        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i];
            if (token .equals("+") || token.equals("-") || token.equals("*") || token.equals("/")) {
                Integer val1 = stack.pop();
                Integer val2 = stack.pop();
                int res = 0;
                switch (token) {
                    case "+" :
                        res = val1 + val2;
                        break;
                    case "-" :
                        res = val2 - val1;
                        break;
                    case "*" :
                        res = val1 * val2;
                        break;
                    case "/" :
                        res = val2 / val1;
                        break;
                }
                stack.push(res);
            } else {
                stack.push(Integer.valueOf(token));
            }
        }
        return stack.pop();
    }
}
