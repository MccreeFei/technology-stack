package cn.mccreefei.technologystack.leetcode;

import java.util.LinkedList;

/**
 * @author MccreeFei
 * @create 2020-01-06 下午2:23
 * @refer <href>https://leetcode.com/problems/132-pattern/</href>
 * @idea 使用栈 从后向前遍历维护最大的s3使得前面有s2>s3,只需找出是否存在s1小于s3即可
 */
public class OneThreeTwoPattern {
    public boolean find132pattern(int[] nums) {
        if (nums == null || nums.length < 3) {
            return false;
        }
        int s3 = Integer.MIN_VALUE;
        LinkedList<Integer> stack = new LinkedList<>();
        stack.push(nums[nums.length - 1]);
        for (int i = nums.length - 1; i >= 0; i--) {
            if (nums[i] < s3) {
                return true;
            }
            while (!stack.isEmpty() && nums[i] > stack.peek()) {
                s3 = stack.pop();
            }
            stack.push(nums[i]);
        }
        return false;
    }
}
