package cn.mccreefei.technologystack.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author MccreeFei
 * @create 2019-07-19 上午11:55
 * @refer <href>https://leetcode.com/problems/combination-sum-ii/</href>
 * @idea 回溯法
 */
public class CombinationSumII {
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(candidates);
        dfs(result, new ArrayList<>(), candidates, 0, target);
        return result;
    }

    private void dfs(List<List<Integer>> result, List<Integer> lst, int[] candidates, int start, int target) {
        if (target == 0) {
            result.add(new ArrayList<>(lst));
            return;
        }
        if (target < 0 || start >= candidates.length || candidates[start] > target) {
            return;
        }
        for (int i = start; i < candidates.length; i++) {
            //去除重复
            if (i > start && candidates[i] == candidates[i-1]) {
                continue;
            }
            lst.add(candidates[i]);
            dfs(result, lst, candidates, i+1, target-candidates[i]);
            lst.remove(lst.size()-1);
        }
    }

}
