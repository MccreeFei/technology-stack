package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2020-04-09 下午3:34
 * @refer <href>https://leetcode.com/problems/backspace-string-compare/</href>
 * @idea 从后向前对比 记录'#'个数, '#'>0时删减字符
 */
public class BackspaceStringCompare {
    public boolean backspaceCompare(String S, String T) {
        char[] s = S.toCharArray();
        char[] t = T.toCharArray();
        int i = s.length - 1;
        int j = t.length - 1;
        int sBack = 0, tBack = 0;
        while (i >= 0 || j >= 0) {
            while (i >= 0 && (sBack > 0 || s[i] == '#')) {
                if (s[i] == '#') {
                    sBack++;
                } else {
                    sBack--;
                }
                i--;
            }
            while (j >= 0 && (tBack > 0 || t[j] == '#')) {
                if (t[j] == '#') {
                    tBack++;
                } else {
                    tBack--;
                }
                j--;
            }
            if (i < 0 && j < 0) {
                return true;
            } else if (i < 0 || j < 0) {
                return false;
            }
            if (s[i] != t[j]) {
                return false;
            }
            i--;
            j--;
        }
        return true;
    }
}
