package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2019-09-03 上午9:36
 * @refer <href>https://leetcode.com/problems/product-of-array-except-self/</href>
 * @idea temp[i] 保存前i个元素的乘积, result[i]在第一次遍历时存放i后所有元素乘积， 最终result[i] = result[i] * temp[i].
 */
public class ProductOfArrayExceptSelf {
    public int[] productExceptSelf(int[] nums) {
        int[] temp = new int[nums.length];
        int[] result = new int[nums.length];
        temp[0] = 1;
        result[nums.length - 1] = 1;

        for (int i = 1; i < nums.length; i++) {
            temp[i] = nums[i-1] * temp[i-1];
        }

        for (int i = nums.length - 2; i >= 0; i--) {
            result[i] = result[i+1] * nums[i+1];
        }

        for (int i = 0; i < nums.length; i++) {
            result[i] = result[i] * temp[i];
        }
        return result;
    }
}
