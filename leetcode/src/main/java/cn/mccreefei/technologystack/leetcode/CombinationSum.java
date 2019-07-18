package cn.mccreefei.technologystack.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author MccreeFei
 * @create 2019-07-18 上午9:43
 * @refer <href>https://leetcode.com/problems/combination-sum/</href>
 * @idea dfs回溯法
 */
public class CombinationSum {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        Arrays.sort(candidates);
        List<List<Integer>> result = new ArrayList<>();
        dfs(result, new ArrayList<>(), candidates, 0, target);
        return result;
    }

    private void dfs(List<List<Integer>> result, List<Integer> lst, int[] candidates, int start, int target) {
        if (target == 0) {
            result.add(new ArrayList<>(lst));
            return;
        }
        if (candidates[start] > target) {
            return;
        }
        for (int i = start; i < candidates.length; i++) {
            lst.add(candidates[i]);
            dfs(result, lst, candidates, i, target-candidates[i]);
            lst.remove(lst.size()-1);
        }
    }
}
