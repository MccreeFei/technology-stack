package cn.mccreefei.technologystack.leetcode;

import java.util.Arrays;

/**
 * @author MccreeFei
 * @create 2020-01-08 上午10:11
 * @refer <href>https://leetcode.com/problems/3sum-closest/</href>
 * @idea 同ThreeSum,排序,前后指针,小于target前指针+1,大于target后指针-1
 */
public class ThreeSumClosest {
    public int threeSumClosest(int[] nums, int target) {
        if (nums == null || nums.length < 3) {
            return 0;
        }
        int result = 0;
        int absMin = Integer.MAX_VALUE;
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 2; i++) {
            if (i > 0 && nums[i] == nums[i-1]) {
                continue;
            }
            int start = i + 1;
            int end = nums.length - 1;
            while (start < end) {
                int sum = nums[start] + nums[end] + nums[i];
                if (sum == target) {
                    return sum;
                } else if (sum < target) {
                    start ++;
                } else {
                    end --;
                }
                int abs = Math.abs(sum - target);
                if (abs < absMin) {
                    result = sum;
                    absMin = abs;
                }
            }
        }
        return result;
    }

}
