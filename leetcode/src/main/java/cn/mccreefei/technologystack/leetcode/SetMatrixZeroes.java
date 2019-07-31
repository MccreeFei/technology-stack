package cn.mccreefei.technologystack.leetcode;

import java.util.Arrays;

/**
 * @author MccreeFei
 * @create 2019-07-31 下午5:14
 * @refer <href>https://leetcode.com/problems/set-matrix-zeroes/</href>
 * @idea 先记录首行首列为0状态 再用首行首列记录该行该列0状态 最后设置首行首列的值 时间O(m*n) 空间 O(1)
 */
public class SetMatrixZeroes {
    public void setZeroes(int[][] matrix) {
        boolean isFirstRowZero = false;
        boolean isFirstColZero = false;
        //先判断第一行第一列是否全为0
        int m = matrix.length;
        int n = matrix[0].length;
        for (int i = 0; i < m; i++) {
            if (matrix[i][0] == 0) {
                isFirstColZero = true;
                break;
            }
        }

        for (int i = 0; i < n; i++) {
            if (matrix[0][i] == 0) {
                isFirstRowZero = true;
                break;
            }
        }

        //使用第一行第一列记录 该行该列是否为0
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][j] == 0) {
                    matrix[0][j] = 0;
                    matrix[i][0] = 0;
                }
            }
        }

        //根据第一行第一列判断其他是否为0
        for (int i = 1; i < m; i++) {
           for (int j = 1; j < n; j++) {
               if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                   matrix[i][j] = 0;
               }
           }
        }
        //设置第一行第一列是否为0
        if (isFirstColZero) {
            for (int i = 0; i < m; i++) {
                matrix[i][0] = 0;
            }
        }
        if (isFirstRowZero) {
            for (int i = 0; i < n; i++) {
                matrix[0][i] = 0;
            }
        }
    }

}
