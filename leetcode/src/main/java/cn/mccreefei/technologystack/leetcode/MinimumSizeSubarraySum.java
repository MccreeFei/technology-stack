package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2019-08-19 上午10:35
 * @refer <href>https://leetcode.com/problems/minimum-size-subarray-sum/</href>
 * @idea 滑动窗口 当sum < s 右边进 sum+ 否则 左边出 记录过程中的最小值
 */
public class MinimumSizeSubarraySum {
    public int minSubArrayLen(int s, int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int start = 0;
        int end = 0;
        int sum = 0;
        int min = Integer.MAX_VALUE;
        while (end < nums.length) {
            sum += nums[end++];

            while (sum >= s) {
                min = Math.min(min, end - start);
                sum -= nums[start++];
            }
        }
        return min == Integer.MAX_VALUE ? 0 : min;

    }
}
