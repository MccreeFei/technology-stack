package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2019-07-27 上午11:50
 * @refer <href>https://leetcode.com/problems/jump-game/</href>
 * @idea max记录可到达的最大最值
 */
public class JumpGame {
    public boolean canJump(int[] nums) {
        int max = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (max < i) {
                return false;
            }
            max = Math.max(max, i + nums[i]);
        }
        return max >= nums.length - 1;
    }

}
