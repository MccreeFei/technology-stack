package cn.mccreefei.technologystack.leetcode;

import java.util.LinkedList;
import java.util.List;

/**
 * @author MccreeFei
 * @create 2020-08-06 下午7:10
 * @refer <href>https://leetcode.com/problems/find-all-duplicates-in-an-array/</href>
 * @idea 依次遍历每个数，每个数位置上对应的数组元素加上整个数组长度+1, 当该数/n == 2时代表出现两次
 */
public class FindAllDuplicatesInAnArray {
    public List<Integer> findDuplicates(int[] nums) {
        List<Integer> result = new LinkedList<>();
        int n = nums.length + 1;
        for (int num : nums) {
            num %= n;
            nums[num - 1] += n;
            if (nums[num - 1] / n == 2) {
                result.add(num);
            }
        }
        return result;
    }
}
