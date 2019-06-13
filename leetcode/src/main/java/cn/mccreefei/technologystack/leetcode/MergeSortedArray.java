package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2019-06-12 下午2:34
 * @refer <href>https://leetcode.com/problems/merge-sorted-array/</href>
 * @idea 从后向前 比较 merge
 */
public class MergeSortedArray {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        for (int i = m+n-1; i >=0 ; i--) {
            if (m < 1) {
                nums1[i] = nums2[--n];
            }else if (n < 1) {
                nums1[i] = nums1[--m];
            }else {
                if (nums1[m-1] > nums2[n-1]) {
                    nums1[i] = nums1[--m];
                }else {
                    nums1[i] = nums2[--n];
                }
            }
        }
    }
}
