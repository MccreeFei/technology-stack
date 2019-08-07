package cn.mccreefei.technologystack.leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author MccreeFei
 * @create 2019-08-07 上午10:09
 * @refer <href>https://leetcode.com/problems/path-sum-ii/</href>
 * @idea 回溯法
 */
public class PathSumII {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        List<List<Integer>> result = new ArrayList<>();
        dfs(result, new LinkedList<>(), root, sum);
        return result;
    }

    private void dfs(List<List<Integer>> result, List<Integer> lst, TreeNode node, int sum) {
        if (node == null) {
            return;
        }
        lst.add(node.val);
        if (node.left == null && node.right == null && node.val == sum) {
            result.add(new ArrayList<>(lst));
        } else {
            dfs(result, lst, node.left, sum - node.val);
            dfs(result, lst, node.right, sum - node.val);
        }
        lst.remove(lst.size() - 1);
    }
}
