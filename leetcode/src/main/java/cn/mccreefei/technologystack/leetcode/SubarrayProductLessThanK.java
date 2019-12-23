package cn.mccreefei.technologystack.leetcode;

import java.util.Arrays;

/**
 * @author MccreeFei
 * @create 2019-12-23 下午6:21
 * @refer <href>https://leetcode.com/problems/subarray-product-less-than-k/</href>
 * @idea 滑动窗口 保证i->j窗口的乘积小于k,计算窗口中的子序列数=(j-i+1),保证最右数被包含的子序列
 * 比如窗口中的数为(2,5,6)则含有(2,5,6),(5,6),(6)三个子序列
 */
public class SubarrayProductLessThanK {
    public int numSubarrayProductLessThanK(int[] nums, int k) {
        int i = 0;
        int product = 1;
        int count = 0;
        for (int j = 0; j < nums.length; j++) {
            product *= nums[j];
            while (j >= i && product >= k) {
                product /= nums[i++];
            }
            count += j - i + 1;
        }
        return count;
    }

}
