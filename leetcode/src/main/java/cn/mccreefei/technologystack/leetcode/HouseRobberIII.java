package cn.mccreefei.technologystack.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author MccreeFei
 * @create 2019-10-29 上午11:25
 * @refer <href>https://leetcode.com/problems/house-robber-iii/</href>
 * @idea dp or dfs
 */
public class HouseRobberIII {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public int rob(TreeNode root) {
        int[] res = help(root);
        return Math.max(res[0], res[1]);
    }

    /**
     * res[0] root不被盗最大收益 res[1] root被盗最大收益
     **/
    private int[] help(TreeNode root) {
        int[] res = new int[2];
        if (root == null) {
            return res;
        }
        int[] left = help(root.left);
        int[] right = help(root.right);
        res[0] = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
        res[1] = root.val + left[0] + right[0];
        return res;
    }

    /**
     * solution2 : dfs 伴随 map记录中间计算值
     */
    public int robSolution2(TreeNode root) {
        Map<TreeNode, Integer> map = new HashMap<>();
        return helpSolution2(root, map);
    }

    private int helpSolution2(TreeNode root, Map<TreeNode, Integer> map) {
        if (root == null) {
            return 0;
        }
        Integer val = map.get(root);
        if (val != null) {
            return val;
        }
        /*
        root not robbed
         */
        int rootNotRobbed = helpSolution2(root.left, map) + helpSolution2(root.right, map);
        /*
        root robbed
         */
        int rootRobbed = root.val;
        if (root.left != null) {
            rootRobbed += helpSolution2(root.left.left, map);
            rootRobbed += helpSolution2(root.left.right, map);
        }
        if (root.right != null) {
            rootRobbed += helpSolution2(root.right.left, map);
            rootRobbed += helpSolution2(root.right.right, map);
        }
        int result = Math.max(rootNotRobbed, rootRobbed);
        map.put(root, result);
        return result;
    }
}
