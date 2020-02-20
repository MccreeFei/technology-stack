package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2020-02-20 上午11:07
 * @refer <href>https://leetcode.com/problems/count-and-say/</href>
 * @idea 递归 + StringBuilder
 */
public class CountAndSay {
    public String countAndSay(int n) {
        if (n == 1) {
            return String.valueOf(1);
        }
        String str = countAndSay(n - 1);
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < str.length()) {
            int val = Character.getNumericValue(str.charAt(i));
            int count = 1;
            while(i + 1 < str.length() && str.charAt(i+1) == str.charAt(i)) {
                i++;
                count++;
            }
            sb.append(count).append(val);
            i++;
        }
        return sb.toString();
    }

}
