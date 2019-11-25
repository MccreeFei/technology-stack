package cn.mccreefei.technologystack.leetcode;

import java.util.LinkedList;
import java.util.List;

/**
 * @author MccreeFei
 * @create 2019-11-25 下午7:10
 * @refer <href>https://leetcode.com/problems/spiral-matrix/</href>
 * @idea 依次遍历 控制边界
 */
public class SpiralMatrix {
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> result = new LinkedList<>();
        int top = 0;
        int left = 0;
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return result;
        }
        int right = matrix[0].length - 1;
        int bottom = matrix.length - 1;


        while (top <= bottom && left <= right) {
            for (int i = left; i <= right; i++) {
                result.add(matrix[top][i]);
            }
            top++;
            for (int j = top; j <= bottom; j++) {
                result.add(matrix[j][right]);
            }
            right--;
            for (int i = right; i >= left && top <= bottom; i--) {
                result.add(matrix[bottom][i]);
            }
            bottom--;
            for (int j = bottom; j >= top && left <= right; j--) {
                result.add(matrix[j][left]);
            }
            left++;
        }
        return result;
    }

}
