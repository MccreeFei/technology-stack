package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2019-07-17 下午5:45
 * @refer <html>https://leetcode.com/problems/search-in-rotated-sorted-array/</html>
 * @idea 先找出target在哪个片段有序 再在有序片段中使用二分法查找
 */
public class SearchInRotatedSortedArray {
    public int search(int[] nums, int target) {
        int start = 0;
        int end = nums.length - 1;
        while (end >= start) {
            int mid = (start + end) / 2;
            //mid左方有序
            if (nums[mid] >= nums[start]) {
                //target在左方有序片段中
                if (target >= nums[start] && target <= nums[mid]) {
                    return searchInSort(nums, target, start, mid);
                } else {
                    start = mid + 1;
                }
                //mid右方有序
            } else {
                //target在右方有序片段中
                if (target >= nums[mid] && target <= nums[end]) {
                    return searchInSort(nums, target, mid, end);
                } else {
                    end = mid - 1;
                }
            }
        }
        return -1;
    }

    //有序片段二分法查找
    private int searchInSort(int[]nums, int target, int start, int end) {
        while (end >= start) {
            int mid = (start + end) / 2;
            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }
        return -1;
    }
}
