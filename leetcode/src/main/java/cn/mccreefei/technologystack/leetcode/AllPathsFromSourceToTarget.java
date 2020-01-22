package cn.mccreefei.technologystack.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MccreeFei
 * @create 2020-01-22 下午9:02
 * @refer <href>https://leetcode.com/problems/all-paths-from-source-to-target/</href>
 * @idea dfs 回溯法
 */
public class AllPathsFromSourceToTarget {
    public List<List<Integer>> allPathsSourceTarget(int[][] graph) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> lst = new ArrayList<>();
        lst.add(0);
        dfs(graph, lst, result, 0);
        return result;
    }

    private void dfs(int[][] graph, List<Integer> lst, List<List<Integer>> res, int node) {
        if (node == graph.length - 1) {
            res.add(new ArrayList<>(lst));
            return;
        }
        for (int n : graph[node]) {
            lst.add(n);
            dfs(graph, lst, res, n);
            lst.remove(lst.size() - 1);
        }
    }
}
