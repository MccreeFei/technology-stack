package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2019-11-11 下午5:38
 * @refer <href>https://leetcode.com/problems/diameter-of-binary-tree/</href>
 * @idea longestPath记录节点的最大路径(左子树或右子树), 期间记录最大直径(左子树最大路径+右子树最大路径)
 */
public class DiameterOfBinaryTree {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }
    private int max = 0;

    public int diameterOfBinaryTree(TreeNode root) {
        if (root == null) {
            return 0;
        }
        longestPath(root);
        return max;
    }

    private int longestPath(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left = longestPath(root.left);
        int right = longestPath(root.right);
        int maxDepth = Math.max(left, right) + 1;
        max = Math.max(max, left + right);
        return maxDepth;
    }
}
