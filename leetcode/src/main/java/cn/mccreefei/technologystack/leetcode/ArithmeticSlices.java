package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2020-01-26 下午2:10
 * @refer <href>https://leetcode.com/problems/arithmetic-slices/</href>
 * @idea cur++,total+=cur 很精髓 得细品
 */
public class ArithmeticSlices {
    public int numberOfArithmeticSlices(int[] A) {
        int cur = 0, total = 0;
        for (int i = 2; i < A.length; i++) {
            if (A[i] - A[i - 1] == A[i - 1] - A[i - 2]) {
                cur++;
                total += cur;
            } else {
                cur = 0;
            }
        }
        return total;
    }
}
