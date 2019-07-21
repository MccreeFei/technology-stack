package cn.mccreefei.technologystack.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MccreeFei
 * @create 2019-07-21 上午11:54
 * @refer <href>https://leetcode.com/problems/permutations/</href>
 * @idea 回溯法
 */
public class Permutations {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        dfs(result, new ArrayList<>(), nums);
        return result;
    }

    private void dfs(List<List<Integer>> result, List<Integer> lst, int[] nums) {
        if (lst.size() == nums.length) {
            result.add(new ArrayList<>(lst));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (lst.contains(nums[i])) {
                continue;
            }
            lst.add(nums[i]);
            dfs(result, lst, nums);
            lst.remove(lst.size()-1);
        }
    }
}
