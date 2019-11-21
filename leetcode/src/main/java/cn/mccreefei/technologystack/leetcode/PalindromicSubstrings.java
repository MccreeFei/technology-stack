package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2019-11-21 下午5:06
 * @refer <href>https://leetcode.com/problems/palindromic-substrings/</href>
 * @idea 从每个字符为中心出发，分别向外扩充计算偶数与奇数的回文数量
 */
public class PalindromicSubstrings {
    private int count = 0;
    public int countSubstrings(String s) {
        final char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            calculate(chars, i, i);
            calculate(chars, i, i+1);
        }
        return count;
    }

    private void calculate(char[] chars, int left, int right) {
        while (left >= 0 && right < chars.length && chars[left] == chars[right]) {
            count++;
            left--;
            right++;
        }
    }
}
