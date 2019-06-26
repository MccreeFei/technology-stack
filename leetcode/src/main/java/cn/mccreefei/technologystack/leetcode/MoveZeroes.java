package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2019-06-26 下午5:54
 * @refer <href>https://leetcode.com/problems/move-zeroes/</href>
 * @idea 使用一个指针记录非0有效值
 */
public class MoveZeroes {
    public void moveZeroes(int[] nums) {
        int start = -1;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                nums[++start] = nums[i];
            }
        }
        for (int i = start + 1; i < nums.length; i++) {
            nums[i] = 0;
        }
    }

}
