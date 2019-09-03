package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2019-09-03 上午10:31
 * @refer <href>https://leetcode.com/problems/search-a-2d-matrix-ii/</href>
 * @idea 从矩阵的左下角开始，target > matrix[i][j] 则在右方寻找 否则在上方寻找 直至找到等于target的数或者超出边界(即找不到)
 */
public class SearchA2DMatrixII {
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }
        int row = matrix.length;
        int col = matrix[0].length;
        int rowIndex = row - 1;
        int colIndex = 0;
        return help(matrix, col, rowIndex, colIndex, target);
    }

    private boolean help(int[][] matrix, int col, int rowIndex, int colIndex, int target) {
        if (matrix[rowIndex][colIndex] == target) {
            return true;
        }
        if (target < matrix[rowIndex][colIndex]) {
            return rowIndex > 0 ? help(matrix, col, --rowIndex, colIndex, target) : false;
        }
        return colIndex < col - 1 ? help(matrix, col, rowIndex, ++colIndex, target) : false;
    }
}
