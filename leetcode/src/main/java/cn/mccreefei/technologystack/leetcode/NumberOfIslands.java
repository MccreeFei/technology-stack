package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2019-08-15 上午11:29
 * @refer <href>https://leetcode.com/problems/number-of-islands/</href>
 * @idea dfs每个1周围的格子 遇1置0
 */
public class NumberOfIslands {
    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        int rows = grid.length;
        int cols = grid[0].length;
        int res = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == '1') {
                    res++;
                    dfs(grid, i, j, rows, cols);
                }
            }
        }
        return res;
    }

    private void dfs(char[][] grid, int i, int j, int rows, int cols) {
        if (i < 0 || j < 0 || i > rows - 1 || j > cols - 1 || grid[i][j] == '0') {
            return;
        }
        grid[i][j] = '0';
        dfs(grid, i-1, j, rows, cols);
        dfs(grid, i+1, j, rows, cols);
        dfs(grid, i, j-1, rows, cols);
        dfs(grid, i, j+1, rows, cols);
    }
}
