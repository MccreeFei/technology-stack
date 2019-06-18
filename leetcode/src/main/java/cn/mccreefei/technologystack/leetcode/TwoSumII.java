package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2019-06-18 上午10:31
 * @refer <href>https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/</href>
 * @idea 双指针
 */
public class TwoSumII {
    public int[] twoSum(int[] numbers, int target) {
        int[] result = new int[2];
        int left = 0;
        int right = numbers.length - 1;
        while (left < right) {
            if (numbers[left] + numbers[right] < target) {
                left++;
            }else if (numbers[left] + numbers[right] > target) {
                right--;
            }else {
                result[0] = left+1;
                result[1] = right+1;
                break;
            }
        }
        return result;
    }
}
