package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2020-03-04 上午9:27
 * @refer <href>https://leetcode.com/problems/game-of-life/</href>
 * @idea 使用两个bit记录状态 初始状态00 01 变化后的状态可能 10 11 核心思路：int中保留之前状态 全部更新完状态后把原状态全部删除即可 达到in-place
 */
public class GameOfLife {
    public void gameOfLife(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                int liveCount = countLive(board, i, j);
                if (board[i][j] == 0 && liveCount == 3) {
                    board[i][j] = 2;
                } else if (board[i][j] == 1 && (liveCount == 2 || liveCount == 3)) {
                    board[i][j] = 3;
                }
            }
        }
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = board[i][j] >> 1;
            }
        }
    }

    private int countLive(int[][] board, int i, int j) {
        int count = 0;
        count += doCount(board, i - 1, j - 1);
        count += doCount(board, i - 1, j);
        count += doCount(board, i - 1, j + 1);
        count += doCount(board, i, j - 1);
        count += doCount(board, i, j + 1);
        count += doCount(board, i + 1, j - 1);
        count += doCount(board, i + 1, j);
        count += doCount(board, i + 1, j + 1);
        return count;
    }

    private int doCount(int[][] board, int i, int j) {
        if (i >= 0 && i < board.length && j >= 0 && j < board[i].length) {
            return (board[i][j] & 1) == 1 ? 1 : 0;
        }
        return 0;
    }
}
