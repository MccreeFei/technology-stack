package cn.mccreefei.technologystack.leetcode;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author MccreeFei
 * @create 2020-03-11 上午9:26
 * @refer <href>https://leetcode.com/problems/largest-number/</href>
 * @idea 比较a+b和b+a的大小, 注意多个0的情况
 */
public class LargestNumber {
    public String largestNumber(int[] nums) {
        String[] sNums = Arrays.stream(nums).mapToObj(String::valueOf).toArray(String[]::new);
        Arrays.sort(sNums, (a, b) -> (b + a).compareTo(a + b));
        if ("0".equals(sNums[0])) {
            return "0";
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < sNums.length; i++) {
            sb.append(sNums[i]);
        }
        return sb.toString();
    }
}
