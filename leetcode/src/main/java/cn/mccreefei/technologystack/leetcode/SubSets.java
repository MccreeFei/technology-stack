package cn.mccreefei.technologystack.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MccreeFei
 * @create 2019-08-01 上午11:44
 * @refer <href>https://leetcode.com/problems/subsets/</href>
 * @idea 回溯法
 */
public class SubSets {
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        dfs(result, new ArrayList<>(), nums, 0);
        return result;
    }

    private void dfs(List<List<Integer>> result, List<Integer> lst, int[] nums, int start) {
        result.add(new ArrayList<>(lst));
        for (int i = start; i < nums.length; i++) {
            lst.add(nums[i]);
            dfs(result, lst, nums, i+1);
            lst.remove(lst.size()-1);
        }
    }
}
