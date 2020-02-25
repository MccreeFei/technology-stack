package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2020-02-25 下午5:05
 * @refer <href>https://leetcode.com/problems/excel-sheet-column-number/</href>
 * @idea easy
 */
public class ExcelSheetColumnNumber {
    public int titleToNumber(String s) {
        int res = 0;
        for (int i = 0; i < s.length(); i++) {
            res = res * 26 + (s.charAt(i) - 'A') % 26 + 1;
        }
        return res;
    }
}
