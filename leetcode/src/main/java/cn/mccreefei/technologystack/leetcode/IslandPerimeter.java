package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2020-07-07 下午5:51
 * @refer <href>https://leetcode.com/problems/island-perimeter/</href>
 * @idea 统计岛屿数量 每个岛屿统计其右边和下边的相邻岛屿数， 每有一个减去相邻两边
 */
public class IslandPerimeter {
    public int islandPerimeter(int[][] grid) {
        int islands = 0, neighbours = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1) {
                    islands++;
                    if (j < grid[0].length - 1 && grid[i][j+1] == 1) {
                        neighbours++;
                    }
                    if (i < grid.length - 1 && grid[i+1][j] == 1) {
                        neighbours++;
                    }
                }
            }
        }
        return islands * 4 - neighbours * 2;
    }
}
