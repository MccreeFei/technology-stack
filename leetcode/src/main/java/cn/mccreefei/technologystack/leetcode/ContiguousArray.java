package cn.mccreefei.technologystack.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author MccreeFei
 * @create 2020-04-13 下午6:00
 * @refer <href>https://leetcode.com/problems/contiguous-array/</href>
 * @idea 记录sum 出现1时+1 出现0时-1, 维护sum->索引i的map, 出现相同sum的以最早的索引为主
 * 当前面的sum等于当前sum时意味着中间这段序列0,1数量相等 记录出现的最大序列长度
 */
public class ContiguousArray {
    public int findMaxLength(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);
        int sum  = 0, max = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i] == 1 ? 1 : -1;
            if (map.containsKey(sum)) {
                max = Math.max(max, i - map.get(sum));
            }
            map.putIfAbsent(sum, i);
        }
        return max;
    }
}
