package cn.mccreefei.technologystack.leetcode;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @author MccreeFei
 * @create 2019-11-05 上午9:28
 * @refer <href>https://leetcode.com/problems/decode-string/</href>
 * @idea 栈
 */
public class DecodeString {
    public String decodeString(String s) {
        Deque<Integer> countDeque = new LinkedList<>();
        Deque<String> stringDeque = new LinkedList<>();
        String res = "";
        for (int i = 0; i < s.length(); i++) {
            if (Character.isDigit(s.charAt(i))) {
                StringBuilder countBuilder = new StringBuilder();
                while (Character.isDigit(s.charAt(i))) {
                    countBuilder.append(s.charAt(i++));
                }
                countDeque.push(Integer.parseInt(countBuilder.toString()));
                stringDeque.push(res);
                res = "";
            }  else if (s.charAt(i) == ']') {
                String pop = stringDeque.pop();
                Integer count = countDeque.pop();
                for (int j = 0; j < count; j++) {
                    pop += res.toString();
                }
                res = pop;
            } else {
                res += s.charAt(i);
            }
        }
        return res;
    }
}
