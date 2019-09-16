package cn.mccreefei.technologystack.leetcode;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author MccreeFei
 * @create 2019-09-16 下午2:03
 * @refer <href>https://leetcode.com/problems/4sum-ii/</href>
 * @idea 分组 map记录 sum(a+b) -> count C,D遍历时map查询-sum个数 空间换时间 时间复杂度O(n^2) 空间复杂度O(n^2)
 */
public class FourSumII {
    public int fourSumCount(int[] A, int[] B, int[] C, int[] D) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int a : A) {
            for (int b : B) {
                int sum = a + b;
                map.merge(sum, 1, (oldValue, newValue) -> oldValue + 1);
            }
        }

        int count = 0;
        for (int c : C) {
            for (int d : D) {
                int sum = c + d;
                count += map.getOrDefault(-sum, 0);
            }
        }

        return count;
    }
}
