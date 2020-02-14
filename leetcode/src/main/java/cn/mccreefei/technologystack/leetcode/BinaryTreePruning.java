package cn.mccreefei.technologystack.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author MccreeFei
 * @create 2020-02-14 上午9:59
 * @refer <href>https://leetcode.com/problems/binary-tree-pruning/</href>
 * @idea 二叉树的递归
 */
public class BinaryTreePruning {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public TreeNode pruneTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode leftNode = pruneTree(root.left);
        TreeNode rightNode = pruneTree(root.right);
        if (leftNode == null) {
            root.left = null;
        }
        if (rightNode == null) {
            root.right = null;
        }
        if (root.val == 1 || root.left != null || root.right != null) {
            return root;
        }
        return null;
    }

}
