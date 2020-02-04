package cn.mccreefei.technologystack.leetcode;

import java.util.LinkedList;

/**
 * @author MccreeFei
 * @create 2020-02-04 上午10:34
 * @refer <href>https://leetcode.com/problems/basic-calculator-ii/</href>
 * @idea 栈操作
 */
public class BasicCalculatorII {
    public int calculate(String s) {
        LinkedList<Integer> stack = new LinkedList<>();
        StringBuffer numBuffer = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            if (isNum(s.charAt(i))) {
                numBuffer.append(s.charAt(i));
            } else {
               if (numBuffer.length() > 0 && s.charAt(i) != ' ') {
                   stack.push(Integer.valueOf(numBuffer.toString()));
                   numBuffer.delete(0, numBuffer.length());
               }
               if (s.charAt(i) == '*' || s.charAt(i) == '/') {
                   char operator = s.charAt(i);
                   while (!isNum(s.charAt(i))) {
                       i++;
                   }
                   while (i < s.length() && isNum(s.charAt(i))) {
                       numBuffer.append(s.charAt(i++));
                   }
                   i--;
                   Integer num = Integer.valueOf(numBuffer.toString());
                   numBuffer.delete(0, numBuffer.length());
                   if (operator == '*') {
                       stack.push(stack.pop() * num);
                   } else {
                       stack.push(stack.pop() / num);
                   }
               } else if (s.charAt(i) == '-') {
                   numBuffer.append('-');
               }
            }
        }
        if (numBuffer.length() > 0) {
            stack.push(Integer.valueOf(numBuffer.toString()));
        }
        int result = 0;
        while (!stack.isEmpty()) {
            result += stack.pop();
        }
        return result;
    }

    private boolean isNum(char c) {
        return  (c != '+' && c != '-' && c != '*' && c != '/' && c != ' ');
    }

}
