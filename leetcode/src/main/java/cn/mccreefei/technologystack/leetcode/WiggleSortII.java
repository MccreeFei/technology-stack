package cn.mccreefei.technologystack.leetcode;

import java.util.Arrays;

/**
 * @author MccreeFei
 * @create 2020-04-16 下午4:00
 * @refer <href>https://leetcode.com/problems/wiggle-sort-ii/</href>
 * @idea 快速选择寻找中位数 + 荷兰旗 比中位数小的放左边,大的放右边 + 逆序插入数组
 */
public class WiggleSortII {
    public void wiggleSort(int[] nums) {
        int mid = quickSelect(nums, 0, nums.length - 1, nums.length / 2);
        threeWayPartition(nums, mid);
        int midIndex = (nums.length  - 1)/ 2;
        int r = nums.length - 1;
        int[] copy = Arrays.copyOf(nums, nums.length);
        for (int i = 0; i < nums.length; i++) {
            nums[i] = i % 2 == 0 ? copy[midIndex--] : copy[r--];
        }

    }

    /**
     * 快速选择 refer 寻找数组中第k大的数
     */
    private int quickSelect(int[] nums, int left, int right, int targetIndex) {
        if (left == right) {
            return nums[left];
        }
        int target = nums[right];
        int i = left, cur = left;
        while (cur < right) {
            if (nums[cur] < target) {
                swap(nums, i++, cur);
            }
            cur++;
        }
        swap(nums, i, right);
        if (i == targetIndex) {
            return target;
        } else if (i < targetIndex) {
            return quickSelect(nums, i + 1, right, targetIndex);
        } else{
            return quickSelect(nums, left, i - 1, targetIndex);
        }
    }

    /**
     * 荷兰旗 three-way-partition
     */
    private void threeWayPartition(int[] nums, int mid) {
        int left = 0, cur = 0, right = nums.length - 1;
        while (cur <= right) {
            if (nums[cur] < mid) {
                swap(nums, left++, cur++);
            } else if (nums[cur] > mid) {
                swap(nums, cur, right--);
            } else {
                cur++;
            }
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
