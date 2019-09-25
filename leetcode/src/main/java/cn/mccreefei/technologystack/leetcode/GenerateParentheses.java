package cn.mccreefei.technologystack.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MccreeFei
 * @create 2019-09-25 下午8:06
 * @refer <href>https://leetcode.com/problems/generate-parentheses/</href>
 * @idea 回溯法 每个backtrace都代表当前的状态 str:当前的组合,open:当前(个数,close:当前)个数,max:(最大个数
 *       当str的字符个数==6就找到一个结果,当open小于max则可以添加(进入下一个回溯,当)个数小于(个数,则可以添加)
 */
public class GenerateParentheses {
    public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        backtrace(result, "", 0, 0, n);
        return result;
    }

    private void backtrace(List<String> result, String str, int open, int close, int max) {
        if (str.length() == 2 * max) {
            result.add(str);
            return;
        }
        if (open < max) {
            backtrace(result, str + "(", open + 1, close, max);
        }
        if (close < open) {
            backtrace(result, str + ")", open, close + 1, max);
        }
    }
}
