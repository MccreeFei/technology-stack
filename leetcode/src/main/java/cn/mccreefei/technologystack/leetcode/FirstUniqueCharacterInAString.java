package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2020-02-28 下午2:13
 * @refer <href>https://leetcode.com/problems/first-unique-character-in-a-string/</href>
 * @idea 第一次遍历记录每个字符出现的次数 第二次遍历找出index
 */
public class FirstUniqueCharacterInAString {
    public int firstUniqChar(String s) {
        int[] count = new int[26];
        for (int i = 0; i < s.length(); i++) {
            count[s.charAt(i) - 'a']++;
        }
        for (int i = 0; i < s.length(); i++) {
            if (count[s.charAt(i) - 'a'] == 1) {
                return i;
            }
        }
        return -1;
    }
}
