package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2019-12-21 下午3:04
 * @refer <href>https://leetcode.com/problems/single-number-iii/</href>
 * @idea 位运算
 */
public class SingleNumberIII {
    public int[] singleNumber(int[] nums) {
        int[] result = new int[2];
        int k = 0;
        /*
        得到res[0]^res[1]的值
         */
        for (int num : nums) {
            k ^= num;
        }
        /*
        得到res[0]^res[1]最右位为1的数 基于此数分为两组 res[0] res[1]分别在不同组
         */
        k &= -k;
        for (int num : nums) {
            if ((num & k) == 0) {
                result[0] ^= num;
            } else {
                result[1] ^= num;
            }
        }
        return result;
    }
}
