package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2020-07-05 上午10:38
 * @refer <href>https://leetcode.com/problems/ugly-number-ii/</href>
 * @idea 分别记录2,3,5因子索引记录 找出前面各因子*索引的最小值
 */
public class UglyNumberII {
    public int nthUglyNumber(int n) {
        int[] data = new int[n];
        int factor2 = 2, factor2Num = 0;
        int factor3 = 3, factor3Num = 0;
        int factor5 = 5, factor5Num = 0;
        data[0] = 1;
        for (int i = 1; i < n; i++) {
            int min = Math.min(Math.min(factor2 * data[factor2Num], factor3 * data[factor3Num]),  factor5 *data[factor5Num]);
            data[i] = min;
            if (factor2 * data[factor2Num] == min) {
                factor2Num++;
            }
            if (factor3 * data[factor3Num] == min) {
                factor3Num++;
            }
            if (factor5 * data[factor5Num] == min) {
                factor5Num++;
            }
        }
        return data[n - 1];
    }
}
