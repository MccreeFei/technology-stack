package cn.mccreefei.technologystack.leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author MccreeFei
 * @create 2019-12-25 下午6:59
 * @refer <href>https://leetcode.com/problems/3sum-with-multiplicity/</href>
 * @idea countMap记录A[i]+A[j]出现个数,转化为target-k出现的次数之和
 */
public class ThreeSumWithMultiplicity {
    public int threeSumMulti(int[] A, int target) {
        Map<Integer, Integer> countMap = new HashMap<>();
        long res = 0;
        for (int j = 0; j < A.length; j++) {
            res += countMap.getOrDefault(target - A[j], 0);
            for (int i = 0; i < j; i++) {
                countMap.put(A[i] + A[j], countMap.getOrDefault(A[i] + A[j], 0) + 1);
            }
        }
        return (int)(res % 1000000007);
    }
}
