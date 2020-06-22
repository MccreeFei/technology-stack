package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2020-06-22 下午2:33
 * @refer <href>https://leetcode.com/problems/dungeon-game/</href>
 * @idea dp dp[i][j] 表示在(i,j)时最小需要的hp 状态转移方程:
 *       int need = Math.min(dp[i + 1][j], dp[i][j +1]) - dungeon[i][j];
 *       dp[i][j] = need <= 0 ? 1 : need;
 */
public class DungeonGame {
    public int calculateMinimumHP(int[][] dungeon) {
        int m = dungeon.length;
        int n = dungeon[0].length;
        int[][] dp = new int[m][n];
        dp[m - 1][n - 1] = dungeon[m - 1][n - 1] >= 0 ? 1 : 1 - dungeon[m - 1][n - 1];
        for (int i = m - 2; i >= 0; i--) {
            dp[i][n - 1] = Math.max(1, dp[i + 1][n - 1] - dungeon[i][n - 1]);
        }
        for (int j = n - 2; j >= 0; j--) {
            dp[m - 1][j] = Math.max(1, dp[m - 1][j + 1] - dungeon[m - 1][j]);
        }

        for (int i = m - 2; i >=0; i--) {
            for (int j = n - 2; j >= 0; j--) {
                int need = Math.min(dp[i + 1][j], dp[i][j +1]) - dungeon[i][j];
                dp[i][j] = need <= 0 ? 1 : need;
            }
        }
        return dp[0][0];
    }
}
