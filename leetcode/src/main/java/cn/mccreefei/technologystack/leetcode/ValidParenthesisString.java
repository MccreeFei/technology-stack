package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2020-04-16 下午4:32
 * @refer <href>https://leetcode.com/problems/valid-parenthesis-string/</href>
 * @idea 贪心算法 lo表示(最少可能出现的次数 hi表示(最多可能出现的次数
 */
public class ValidParenthesisString {
    public boolean checkValidString(String s) {
        int lo = 0, hi = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                lo++;
                hi++;
            } else if (s.charAt(i) == ')') {
                if (lo > 0) {
                    lo--;
                }
                if (--hi < 0) {
                    return false;
                }
            } else {
                if (lo > 0) {
                    lo--;
                }
                hi++;
            }
        }
        return lo == 0;
    }
}
