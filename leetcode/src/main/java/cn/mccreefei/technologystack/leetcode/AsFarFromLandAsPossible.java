package cn.mccreefei.technologystack.leetcode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author MccreeFei
 * @create 2020-01-31 下午3:22
 * @refer <href>https://leetcode.com/problems/as-far-from-land-as-possible/</href>
 * @idea bfs
 */
public class AsFarFromLandAsPossible {
    public int maxDistance(int[][] grid) {
        LinkedList<int[]> queue = new LinkedList<>();
        int step = 1;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 1) {
                    queue.offer(new int[]{i, j});
                }
            }
        }
        while (!queue.isEmpty()) {
            step++;
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] pop = queue.pop();
                help(grid, pop[0] - 1, pop[1], step, queue);
                help(grid, pop[0] + 1, pop[1], step, queue);
                help(grid, pop[0], pop[1] - 1, step, queue);
                help(grid, pop[0], pop[1] + 1, step, queue);
            }
        }
        int max = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                max = Math.max(max, grid[i][j]);
            }
        }
        return max == 0 ? -1 : max == 1 ? -1 : max - 1;
    }

    private void help(int[][] grid, int i, int j, int step, LinkedList<int[]> queue) {
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[i].length || grid[i][j] != 0) {
            return;
        }
        grid[i][j] = step;
        queue.offer(new int[]{i, j});
    }

}
