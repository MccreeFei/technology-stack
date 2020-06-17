package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2020-06-17 下午3:46
 * @refer <href>https://leetcode.com/problems/surrounded-regions/</href>
 * @idea 先检验四边是O的 然后根据O的四周进行dfs 将O的进行标记 最后将未标记的O设置成X
 */
public class SurroundedRegions {
    public void solve(char[][] board) {
        if (board == null || board.length == 0 || board[0].length == 0) {
            return;
        }
        int m = board.length;
        int n = board[0].length;
        boolean[][] check = new boolean[m][n];
        for (int i = 0; i < m; i++) {
            dfs(board, check, m, n, i, 0);
            dfs(board, check, m, n, i, n - 1);

        }
        for (int j = 0; j < n; j++) {
            dfs(board, check, m, n, 0, j);
            dfs(board, check, m, n, m - 1, j);
        }

        for (int i = 1; i < m - 1; i++) {
            for (int j = 1; j < n - 1; j++) {
                if (board[i][j] == 'O' && !check[i][j]) {
                    board[i][j] = 'X';
                }
            }
        }

    }

    private void dfs(char[][] board, boolean[][] check, int m, int n, int i, int j) {
        if (i < 0 || i >= m || j < 0 || j >= n) {
            return;
        }
        if (board[i][j] == 'X' || check[i][j]) {
            return;
        }
        check[i][j] = true;
        dfs(board, check, m, n, i - 1, j);
        dfs(board, check, m, n, i + 1, j);
        dfs(board, check, m, n, i, j - 1);
        dfs(board, check, m, n, i, j + 1);
    }
}
