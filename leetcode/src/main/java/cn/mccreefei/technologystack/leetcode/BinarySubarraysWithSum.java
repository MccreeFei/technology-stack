package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2020-02-11 上午10:10
 * @refer <href>https://leetcode.com/problems/binary-subarrays-with-sum/</href>
 * @idea 记录prevSum出现次数 sum[0,j] = sum[0][i] + sum[i][j] ==>
 * sum(index 0->j的sum值) = pervSum(index 0->i的sum值 其中i为[0,j)中的任意值) + S
 * 到index j时多少和等于S的连续序列数就等于之前出现过的sum-S的个数
 */
public class BinarySubarraysWithSum {
    public int numSubarraysWithSum(int[] A, int S) {
        if (A == null || A.length < 1 || S < 0) {
            return 0;
        }
        int sum = 0, result = 0;
        int[] prevSumCount = new int[A.length + 1];
        prevSumCount[0] = 1;
        for (int a : A) {
            sum += a;
            if (sum >= S) {
                result += prevSumCount[sum - S];
            }
            prevSumCount[sum]++;
        }
        return result;
    }
}

