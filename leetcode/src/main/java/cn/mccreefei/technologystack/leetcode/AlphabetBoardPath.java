package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2020-02-03 下午5:06
 * @refer <href>https://leetcode.com/problems/alphabet-board-path/</href>
 * @idea 判断界限
 */
public class AlphabetBoardPath {
    public String alphabetBoardPath(String target) {
        StringBuffer sb = new StringBuffer();
        int curI = 0, curJ = 0;
        for (int i = 0; i < target.length(); i++) {
            char c = target.charAt(i);
            int row = (c - 'a') / 5;
            int col = (c - 'a') % 5;
            while (curI != row || curJ != col) {
                //向下走时需判断是否在界限中
                if (curI < row && (curI < 4 || curJ < 1)) {
                    curI++;
                    sb.append("D");
                } else if (curI > row) {
                    curI--;
                    sb.append("U");
                }
                //向右走时需判断是否在界限中
                if (curJ < col && curI < 5) {
                    curJ++;
                    sb.append("R");
                } else if (curJ > col && curI < 5) {
                    curJ--;
                    sb.append("L");
                }
            }
            sb.append("!");
        }
        return sb.toString();
    }
}
