package cn.mccreefei.technologystack.leetcode;

import java.util.Arrays;

/**
 * @author MccreeFei
 * @create 2020-08-17 下午5:23
 * @refer <href>https://leetcode.com/problems/magnetic-force-between-two-balls/</href>
 * @idea 求最小引力的最大值 二分法 最小引力1, 最大引力1000000000， 二分法检验是否满足
 */
public class MagneticForceBetweenTwoBalls {
    public int maxDistance(int[] position, int m) {
        int lo = 1, hi = 1000000000;
        int result = 1;
        Arrays.sort(position);
        while (lo <= hi) {
            int mid = lo + (hi - lo)/2;
            if (check(position, m, mid)) {
                result = mid;
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        return result;
    }

    /**
     * 检验distance是否满足, 即有m个位置之间的距离大于等于distance
     */
    private boolean check(int[] position, int m, int distance) {
        int start = position[0], count = 1;
        for (int i = 1; i < position.length; i++) {
            if (position[i] - start >= distance) {
                if (++count == m) {
                    return true;
                }
                start = position[i];
            }
        }
        return false;
    }
}
