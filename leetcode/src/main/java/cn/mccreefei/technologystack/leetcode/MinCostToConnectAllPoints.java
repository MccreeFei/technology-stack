package cn.mccreefei.technologystack.leetcode;

import java.util.PriorityQueue;

/**
 * @author MccreeFei
 * @create 2020-09-13 下午4:18
 * @refer <href>https://leetcode.com/problems/min-cost-to-connect-all-points/</href>
 * @idea Minimum Spanning Tree 最小生成树
 */
public class MinCostToConnectAllPoints {
    // kruskal + Union Find
    class Edge {
        int u;
        int v;
        int cost;

        public Edge(int u, int v, int cost) {
            this.u = u;
            this.v = v;
            this.cost = cost;
        }
    }

    /**
     * 最初每个节点各自为森林，最小堆维护cost最小的边，依次弹出，将边的顶点进行合并 并且加上cost， 最终达到边的个数==顶点个数-1的条件 完成
     */
    public int minCostConnectPoints(int[][] points) {
        PriorityQueue<Edge> queue = new PriorityQueue<>((a, b) -> a.cost - b.cost);
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                queue.offer(new Edge(i, j, Math.abs(points[i][0] - points[j][0]) + Math.abs(points[i][1] - points[j][1])));
            }
        }
        int[] p = new int[points.length];
        for (int i = 0; i < p.length; i++) {
            p[i] = i;
        }
        int res = 0;
        int edgeNum = 0;
        while (edgeNum < points.length - 1 && !queue.isEmpty()) {
            Edge edge = queue.poll();
            int px = find(p, edge.u);
            int py = find(p, edge.v);
            if (px == py) {
                continue;
            }
            res += edge.cost;
            union(p, px, py);
            edgeNum++;
        }
        return res;

    }

    private int find(int[] p, int x) {
        if (x != p[x]) {
            p[x] = find(p, p[x]);
        }
        return p[x];
    }

    private void union(int[] p, int x, int y) {
        int px = find(p, x);
        int py = find(p, y);
        p[px] = py;
    }

    // Prime
    /**
     * 选取第一个节点为已访问节点，将与已访问节点的组成边推入小顶堆，依次弹出，未访问节点标记访问并且加上cost，直到所有节点都已访问
     */
    public int minCostConnectPoints2(int[][] points) {
        int res = 0;
        boolean[] visited = new boolean[points.length];
        visited[0] = true;
        int visit = 1;
        int prev = 0;
        // int[2] data[0]:cost data[1]:index
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>((a, b) -> a[0] - b[0]);
        while (visit < points.length) {
            for (int i = 0; i < points.length; i++) {
                if (!visited[i]) {
                    priorityQueue.offer(new int[]{Math.abs(points[i][0] - points[prev][0]) + Math.abs(points[i][1] - points[prev][1]), i});
                }
            }
            while (visited[priorityQueue.peek()[1]]) {
                priorityQueue.poll();
            }
            int[] poll = priorityQueue.poll();
            visited[poll[1]] = true;
            prev = poll[1];
            res += poll[0];
            visit++;
        }
        return res;
    }

}
