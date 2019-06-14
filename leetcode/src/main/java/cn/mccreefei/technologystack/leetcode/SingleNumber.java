package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2019-06-14 下午3:17
 * @refer <href>https://leetcode.com/problems/single-number/description/</href>
 * @idea 异或性质 x^x=0 x^0=x 具有交换律 结合律
 */
public class SingleNumber {
    public int singleNumber(int[] nums) {
        int result = 0;
        for (int num : nums) {
            result ^= num;
        }
        return result;
    }
}
