package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2019-06-14 上午11:32
 * @refer <href>https://leetcode.com/problems/valid-palindrome/</href>
 * @idea 头尾双指针
 */
public class ValidPalindrome {
    public boolean isPalindrome(String s) {
        char[] chars = s.toLowerCase().replaceAll("[^a-z0-9]", "").toCharArray();
        for (int i=0, j=chars.length-1; i < j; i++, j--) {
            if (chars[i] != chars[j]) {
                return false;
            }
        }
        return true;
    }
}
