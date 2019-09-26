package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2019-09-26 下午3:23
 * @refer <href>https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/</href>
 * @idea 先二分法找到等于target的位置 然后前后寻址
 */
public class FindFirstAndLastPositionOfElementInSortedArray {
    public int[] searchRange(int[] nums, int target) {
        int[] result = new int[2];
        result[0] = -1;
        result[1] = -1;
        if (nums == null || nums.length == 0) {
          return result;
        }
        int start = 0;
        int end = nums.length - 1;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] < target) {
                start = mid + 1;
            } else if (nums[mid] > target) {
                end = mid - 1;
            } else {
                searchIndex(nums, result, mid);
                return result;
            }
        }
        return result;
    }

    private void searchIndex(int[] nums, int[] result, int index) {
        int i = index - 1;
        int j = index + 1;
        while (i >= 0 && nums[i] == nums[i+1]) {
            i--;
        }
        while (j <= nums.length - 1 && nums[j] == nums[j-1]) {
            j++;
        }
        result[0] = i + 1;
        result[1] = j - 1;
    }
}
