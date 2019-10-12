package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2019-10-12 下午3:04
 * @refer <href>https://leetcode.com/problems/word-search/</href>
 * @idea dfs 注意设置board[i][j]='*',使得遍历不能回头
 */
public class WordSearch {
    public boolean exist(char[][] board, String word) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (exist(board, word, i, j, 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean exist(char[][] board, String word, int i, int j, int wordIndex) {
        if (wordIndex == word.length()) {
            return true;
        }
        if (i < 0 || j < 0 || i == board.length || j == board[0].length || board[i][j] != word.charAt(wordIndex)) {
            return false;
        }
        char tmp = board[i][j];
        board[i][j] = '*';
        boolean res = exist(board, word, i + 1, j, wordIndex + 1)
                || exist(board, word, i - 1, j, wordIndex + 1)
                || exist(board, word, i, j + 1, wordIndex + 1)
                || exist(board, word, i, j - 1, wordIndex + 1);
        board[i][j] = tmp;
        return res;
    }

}
