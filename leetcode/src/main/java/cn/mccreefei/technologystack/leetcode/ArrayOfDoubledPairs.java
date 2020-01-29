package cn.mccreefei.technologystack.leetcode;

import java.util.TreeMap;

/**
 * @author MccreeFei
 * @create 2020-01-29 下午4:08
 * @refer <href>https://leetcode.com/problems/array-of-doubled-pairs/</href>
 * @idea TreeMap a -> count(a)  从小到大遍历 count(a)>count(2a) return false else count(2a)-count(a)
 */
public class ArrayOfDoubledPairs {
    public boolean canReorderDoubled(int[] A) {
        TreeMap<Integer, Integer> countMap = new TreeMap<>();
        int sum = 0;
        for (int a : A) {
            countMap.put(a, countMap.getOrDefault(a, 0) + 1);
            sum += a;
        }
        if (sum % 3 != 0) {
            return false;
        }
        for (int k : countMap.keySet()) {
            int countK = countMap.get(k);
            if (countK == 0) {
                continue;
            }
            if (k == 0 && (countK % 2 == 1)) {
                return false;
            }
            int wanted = k < 0 ? k / 2 : k * 2;
            int countWanted = countMap.getOrDefault(wanted, 0);
            if (countK > countWanted) {
                return false;
            }
            countMap.put(wanted, countWanted - countK);
        }
        return true;
    }
}
