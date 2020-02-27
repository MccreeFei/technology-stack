package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2020-02-27 下午3:41
 * @refer <href>https://leetcode.com/problems/find-peak-element/</href>
 * @idea 选定数组中间位置开始 nums[mid]大于两边即返回mid，如果nums[mid]小于left,
 * 即从mid左侧开始遍历，出现nums[i]>nums[i-1]就找到index i，如果nums[mid]大于right同理
 */
public class FindPeekElement {
    public int findPeakElement(int[] nums) {
        int len = nums.length;
        int mid = (len - 1) / 2;
        int left = mid == 0 ? Integer.MIN_VALUE : nums[mid - 1];
        int right = mid == len - 1 ? Integer.MIN_VALUE : nums[mid + 1];
        if (nums[mid] > left && nums[mid] > right) {
            return mid;
        } else if (nums[mid] < left) {
            for (int i = mid - 1; i > 0; i--) {
                if (nums[i] > nums[i - 1]) {
                    return i;
                }
            }
            return 0;
        } else {
            for (int i = mid + 1; i < len - 1; i++) {
                if (nums[i] > nums[i + 1]) {
                    return i;
                }
            }
            return len - 1;
        }
    }
}
