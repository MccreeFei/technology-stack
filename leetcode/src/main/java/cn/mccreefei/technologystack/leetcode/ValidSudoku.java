package cn.mccreefei.technologystack.leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 * @author MccreeFei
 * @create 2020-04-15 上午10:57
 * @refer <href>https://leetcode.com/problems/valid-sudoku/</href>
 * @idea 使用set辨别是否重复 添加规则:如第0个行矩阵出现num 0r[num] 第一个列矩阵出现num 1c[num] 第二个3x3矩阵出现num 2b[num]
 */
public class ValidSudoku {
    public boolean isValidSudoku(char[][] board) {
        Set<String> set = new HashSet<String>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == '.') {
                    continue;
                }
                if (!set.add(i + "r" + board[i][j]) || !set.add(j + "c" + board[i][j]) || !set.add(((i / 3) * 3 +( j / 3)) + "b" + board[i][j])) {
                    return false;
                }
            }
        }
        return true;
    }
}
