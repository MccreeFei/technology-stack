package cn.mccreefei.technologystack.leetcode;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * @author MccreeFei
 * @create 2020-05-13 下午4:51
 * @refer <href>https://leetcode.com/problems/remove-k-digits/</href>
 * @idea 栈 保持栈中元素单调递增
 */
public class RemoveKDigits {
    public String removeKdigits(String num, int k) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < num.length(); i++) {
            //当k>0并且当前元素小于栈顶元素 弹出栈顶元素 k--
            while (sb.length() > 0 && k > 0 && num.charAt(i) < sb.charAt(sb.length() - 1)) {
                sb.deleteCharAt(sb.length() - 1);
                k--;
            }
            //remove leading zeroes
            if (sb.length() == 0 && num.charAt(i) == '0') {
                continue;
            }
            sb.append(num.charAt(i));
        }
        //此时栈中已经单调非递减 还需要移除元素 从栈顶开始移除
        while (k > 0 && sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
            k--;
        }
        return sb.length() == 0 ? "0" : sb.toString();
    }

}
