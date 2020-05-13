package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2020-05-13 上午10:58
 * @refer <href>https://leetcode.com/problems/single-element-in-a-sorted-array/</href>
 * @idea 二分法
 */
public class SingleElementInASortedArray {
    public int singleNonDuplicate(int[] nums) {
        int left = 0, right = nums.length - 1, n  = nums.length;
        while (left < right) {
            int mid = left + (right - left)/2;
            //判断与右边数是否相等
            if (nums[mid] == nums[mid + 1]) {
                //右边个数为偶数个 则single num在左边否则在右边
                if ((n - mid) % 2 == 0) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            } else {
                //mid==0 或者和左边数不等 则当前为single num
                if (mid == 0 || nums[mid] != nums[mid - 1]) {
                    return nums[mid];
                } else {
                    //左边个数为偶数个 则single num在右边否则在左边
                    if ((mid + 1) % 2 == 0) {
                        left = mid + 1;
                    } else {
                        right = mid - 1;
                    }
                }
            }
        }
        return nums[left];
    }
}
