package cn.mccreefei.technologystack.leetcode;

import java.util.LinkedList;
import java.util.List;

/**
 * @author MccreeFei
 * @create 2019-08-10 下午12:20
 * @refer <href>https://leetcode.com/problems/binary-tree-preorder-traversal/</href>
 * @idea 树的先序遍历
 */
public class BinaryTreePreorderTraversal {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public List<Integer> preorderTraversal(TreeNode root) {
        LinkedList<Integer> result = new LinkedList<>();
        help(result, root);
        return result;
    }

    private void help(List<Integer> result, TreeNode node) {
        if (node == null) {
            return;
        }
        result.add(node.val);
        help(result, node.left);
        help(result, node.right);
    }
}
