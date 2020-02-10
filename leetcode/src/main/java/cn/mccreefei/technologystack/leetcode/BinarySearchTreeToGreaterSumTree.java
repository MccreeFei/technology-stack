package cn.mccreefei.technologystack.leetcode;

import java.util.LinkedList;

/**
 * @author MccreeFei
 * @create 2020-02-10 上午11:31
 * @refer <href>https://leetcode.com/problems/binary-search-tree-to-greater-sum-tree/</href>
 * @idea 中序遍历 + 栈
 */
public class BinarySearchTreeToGreaterSumTree {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public TreeNode bstToGst(TreeNode root) {
        LinkedList<TreeNode> stack = new LinkedList<>();
        inOrderTravel(root, stack);
        int prev = 0;
        while (!stack.isEmpty()) {
            TreeNode pop = stack.pop();
            pop.val += prev;
            prev = pop.val;
        }
        return root;
    }

    private void inOrderTravel(TreeNode root, LinkedList<TreeNode> stack) {
        if (root == null) {
            return;
        }
        inOrderTravel(root.left, stack);
        stack.push(root);
        inOrderTravel(root.right, stack);
    }
}
