package cn.mccreefei.technologystack.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author MccreeFei
 * @create 2019-08-02 上午9:59
 * @refer <href>https://leetcode.com/problems/subsets-ii/</href>
 * @idea 回溯法
 */
public class SubsetsII {
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        dfs(result, new ArrayList<>(), nums, 0);
        return result;
    }

    private void dfs(List<List<Integer>> result, List<Integer> lst, int[] nums, int start) {
        result.add(new ArrayList<>(lst));
        for (int i = start; i < nums.length; i++) {
            if (i > start && nums[i] == nums[i-1]) {
                continue;
            }
            lst.add(nums[i]);
            dfs(result, lst, nums, i+1);
            lst.remove(lst.size()-1);
        }
    }
}
