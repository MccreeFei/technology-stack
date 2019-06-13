package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2019-06-12 下午2:12
 * @refer <href>https://leetcode.com/problems/remove-duplicates-from-sorted-array/</href>
 * @idea 快慢指针
 */

public class RemoveDuplicatesFromSortedArray {
    public int removeDuplicates(int[] nums) {
        if (nums == null) {
            return 0;
        } else if (nums.length ==0 || nums.length == 1) {
            return nums.length;
        }
        int slow = 0;
        for (int fast = 1; fast < nums.length; fast++) {
            if (nums[slow] != nums[fast]) {
                nums[++slow] = nums[fast];
            }
        }
        return ++slow;
    }
}
