package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2019-07-30 下午6:06
 * @refer <href>https://leetcode.com/problems/unique-paths/</href>
 * @idea 动态规划 最下面的和最右边的都是1 中间部分等于其下一个格子的路径值+其右边一个格子的路径值
 */
public class UniquePaths {
    public int uniquePaths(int m, int n) {
        int[][] data = new int[m][n];
        data[m-1][n-1] = 1;
        for (int i = m-2; i >= 0; i--) {
            data[i][n-1] = data[i+1][n-1];
        }
        for (int i = n-2; i >= 0; i--) {
            data[m-1][i] = data[m-1][i+1];
        }

        for (int i = n-2; i >= 0; i--) {
            for (int j = m-2; j >=0; j--) {
                data[j][i] = data[j+1][i] + data[j][i+1];
            }
        }
        return data[0][0];
    }
}
