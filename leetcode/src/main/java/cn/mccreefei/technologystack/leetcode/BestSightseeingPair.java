package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2020-02-07 下午4:42
 * @refer <href>https://leetcode.com/problems/best-sightseeing-pair/</href>
 * @idea 记录前面A[i] + i的最大值
 */
public class BestSightseeingPair {
    public int maxScoreSightseeingPair(int[] A) {
        int prevMaxIndex = 0;
        int maxRes = 0;
        for (int i = 1; i < A.length; i++) {
            maxRes = Math.max(maxRes, prevMaxIndex + A[prevMaxIndex] + A[i] - i);
            if (A[i] + i > prevMaxIndex + A[prevMaxIndex]) {
                prevMaxIndex = i;
            }
        }
        return maxRes;
    }
}
