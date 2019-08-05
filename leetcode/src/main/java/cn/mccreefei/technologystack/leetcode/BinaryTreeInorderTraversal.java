package cn.mccreefei.technologystack.leetcode;

import java.util.LinkedList;
import java.util.List;

/**
 * @author MccreeFei
 * @create 2019-08-05 上午9:38
 * <href>https://leetcode.com/problems/binary-tree-inorder-traversal/</href>
 * @idea 二叉树的中序遍历
 */
public class BinaryTreeInorderTraversal {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new LinkedList<>();
        inOrder(root, result);
        return result;
    }

    private void inOrder(TreeNode root, List<Integer> res) {
        if (root == null) {
            return;
        }
        inOrder(root.left, res);
        res.add(root.val);
        inOrder(root.right, res);
    }
}
