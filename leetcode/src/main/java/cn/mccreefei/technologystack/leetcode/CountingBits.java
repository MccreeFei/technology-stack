package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2019-10-30 下午2:59
 * @refer <href>https://leetcode.com/problems/counting-bits/</href>
 * @idea result[i] = result[i/2] + i%2
 */
public class CountingBits {
    public int[] countBits(int num) {
        int[] result = new int[num+1];
        for (int i = 1; i <= num; i++) {
            result[i] = result[i/2] + i%2;
        }
        return result;
    }
}
