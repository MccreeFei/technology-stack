package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2019-06-21 下午4:54
 * @refer <href>https://leetcode.com/problems/house-robber/description/</href>
 * @idea 动态规划 该处最大值取决于 Math.max(上处偷取最大值, 上上处偷取最大值 + 该处值)
 */
public class HouseRobber {
    public int rob(int[] nums) {
        int last = 0;
        int lastLast = 0;
        int cur = 0;
        for (int i = 0; i < nums.length; i++) {
            cur = Math.max(lastLast + nums[i], last);
            lastLast = last;
            last = cur;
        }
        return cur;
    }
}
