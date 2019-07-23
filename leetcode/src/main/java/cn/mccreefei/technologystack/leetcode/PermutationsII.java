package cn.mccreefei.technologystack.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author MccreeFei
 * @create 2019-07-22 下午4:59
 * @refer <html>https://leetcode.com/problems/permutations-ii/</html>
 * @idea 回溯法 使用visited数组记录每个位置数字的访问状态
 */
public class PermutationsII {
    public List<List<Integer>> permuteUnique(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        boolean[] visited = new boolean[nums.length];
        dfs(result, new ArrayList<>(), nums, visited);
        return result;
    }

    private void dfs(List<List<Integer>> result, List<Integer> lst, int[] nums, boolean[] visited) {
        if (lst.size() == nums.length) {
            result.add(new ArrayList<>(lst));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (visited[i]) {
                continue;
            }
            //比如3个位置的1 结果1,1,1 只会留下位置是（3,2,1）的这个组合
            if (i > 0 && nums[i] == nums[i-1] && visited[i-1]) {
                continue;
            }
            lst.add(nums[i]);
            visited[i] = true;
            dfs(result, lst, nums, visited);
            lst.remove(lst.size()-1);
            visited[i] = false;
        }
    }

}
