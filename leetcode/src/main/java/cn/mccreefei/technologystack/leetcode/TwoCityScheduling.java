package cn.mccreefei.technologystack.leetcode;

import java.util.PriorityQueue;

/**
 * @author MccreeFei
 * @create 2020-06-03 下午4:06
 * @refer <href>https://leetcode.com/problems/two-city-scheduling/</href>
 * @idea 首先假定都是去a点, 然后挑选出一半的人去b点， cost = costs[i][1] - costs[i][0],
 *       确保一半的人cost和最小即可 使用最大堆
 */
public class TwoCityScheduling {
    public int twoCitySchedCost(int[][] costs) {
        int n = costs.length;
        PriorityQueue<Integer> queue = new PriorityQueue<>((a, b) -> b - a);
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += costs[i][0];
            queue.offer(costs[i][1] - costs[i][0]);
            if (queue.size() > n/2) {
                queue.poll();
            }
        }
        while (!queue.isEmpty()) {
            sum += queue.poll();
        }
        return sum;
    }
}
