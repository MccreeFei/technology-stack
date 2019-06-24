package cn.mccreefei.technologystack.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author MccreeFei
 * @create 2019-06-24 上午10:43
 * @refer <href>https://leetcode.com/problems/contains-duplicate-ii/</href>
 * @idea map保存数字->索引的映射
 */
public class ContainDuplicateII {
    /**
     * num->index
     */
    private Map<Integer, Integer> map = new HashMap<>();

    public boolean containsNearbyDuplicate(int[] nums, int k) {
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(nums[i])) {
                Integer lastIndex = map.get(nums[i]);
                if (i - lastIndex <= k) {
                    return true;
                } else {
                    map.put(nums[i], i);
                }
            } else {
                map.put(nums[i], i);
            }
        }
        return false;
    }
}

