package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2019-07-16 下午5:06
 * @refer <href>https://leetcode.com/problems/next-permutation/</href>
 * @idea 找到第一个比右方元素大的索引，此元素与比该元素大并且最小的元素互换，最后该索引右边元素翻转成从小到大顺序
 */
public class NextPermutation {
    public void nextPermutation(int[] nums) {
        if (nums == null || nums.length < 2) {
            return;
        }

        int i = nums.length - 2;
        //找到从右向左第一个比右边数字大的索引 此索引左边的元素是不需要改变的
        while (i >= 0 && nums[i] >= nums[i+1]) {
            i--;
        }
        //此时整个数组的排列是最大的，直接inverse翻转
        if (i < 0) {
            inverse(nums, 0, nums.length - 1);
            return;
        }

        int j = nums.length - 1;
        //找到从右向左第一个比nums[i]大的数，与之交换
        while (nums[j] <= nums[i]) {
            j--;
        }
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
        //i后位置直接翻转成由小到大顺序既可
        inverse(nums, i+1, nums.length-1);
    }

    private void inverse(int[] nums, int start, int end) {
        while (end > start) {
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            start++;
            end--;
        }
    }
}
