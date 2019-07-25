package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2019-07-25 下午5:54
 * @refer <href>https://leetcode.com/problems/rotate-image/</href>
 * @idea 先i,j对换 沿着对角线对换 再水平沿着中间竖线对换 j=n-1-j
 */
public class RotateImage {
    public void rotate(int[][] matrix) {
        int n = matrix.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n/2; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[i][n - 1 -j];
                matrix[i][n - 1 -j] = temp;
            }
        }
    }
}
