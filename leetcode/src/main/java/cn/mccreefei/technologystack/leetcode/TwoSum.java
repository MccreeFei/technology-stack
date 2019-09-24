package cn.mccreefei.technologystack.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author MccreeFei
 * @create 2019-09-24 下午6:59
 * @refer <href>https://leetcode.com/problems/two-sum/</href>
 * @idea map记录nums[i] -> i 时间O(n) 空间O(n)
 */
public class TwoSum {
    public int[] twoSum(int[] nums, int target) {
        int[] result = new int[2];
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            Integer index = map.get(target - nums[i]);
            if (index != null) {
                result[0] = i;
                result[1] = index;
                return result;
            } else {
                map.put(nums[i], i);
            }
        }
        return result;
    }
}
