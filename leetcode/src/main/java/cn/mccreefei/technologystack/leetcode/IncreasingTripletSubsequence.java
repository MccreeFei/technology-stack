package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2019-09-09 上午9:41
 * @refer <href>https://leetcode.com/problems/increasing-triplet-subsequence/</href>
 * @idea 只要n2改变 那么必定存在n1小于n2 所以只要有数大于n2 则存在这个序列
 */
public class IncreasingTripletSubsequence {
    public boolean increasingTriplet(int[] nums) {
        if (nums == null || nums.length < 3) {
            return false;
        }
        int n1 = Integer.MAX_VALUE;
        int n2 = Integer.MAX_VALUE;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] <= n1) {
                n1 = nums[i];
            } else if (nums[i] <= n2) {
                n2 = nums[i];
            } else {
                return true;
            }
        }
        return false;
    }
}
