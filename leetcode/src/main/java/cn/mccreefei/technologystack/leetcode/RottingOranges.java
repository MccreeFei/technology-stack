package cn.mccreefei.technologystack.leetcode;

import java.util.LinkedList;

/**
 * @author MccreeFei
 * @create 2020-08-10 上午10:18
 * @refer <href>https://leetcode.com/problems/rotting-oranges/</href>
 * @idea bfs
 */
public class RottingOranges {
    public int orangesRotting(int[][] grid) {
        LinkedList<int[]> queue = new LinkedList<>();
        int res = 0, fresh = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1) {
                    fresh++;
                } else if (grid[i][j] == 2) {
                    queue.offer(new int[]{i, j});
                }
            }
        }
        if (fresh == 0) {
            return res;
        }
        while (!queue.isEmpty()) {
            res++;
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] rotten = queue.poll();
                fresh -= help(grid, rotten[0] - 1, rotten[1], queue);
                fresh -= help(grid, rotten[0] + 1, rotten[1], queue);
                fresh -= help(grid, rotten[0], rotten[1] - 1, queue);
                fresh -= help(grid, rotten[0], rotten[1] + 1, queue);
            }
            if (fresh == 0) {
                return res;
            }
        }
        return -1;
    }

    /**
     * 判断[i,j]位置是否是1 如果是则改为2,并且队列中添加新rotten orange,返回新添加的个数1
     */
    private int help(int[][] grid, int i, int j, LinkedList<int[]> queue) {
        if (i < 0 || i > grid.length - 1 || j < 0 || j > grid[0].length - 1) {
            return 0;
        }
        if (grid[i][j] == 1) {
            grid[i][j] = 2;
            queue.offer(new int[]{i, j});
            return 1;
        }
        return 0;
    }
}
