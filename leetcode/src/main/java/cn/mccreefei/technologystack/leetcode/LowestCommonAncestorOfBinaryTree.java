package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2019-09-02 上午10:20
 * @refer <href>https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/</href>
 * @idea 深度优先遍历二叉树，一旦找到了两个节点其中的一个，就将这个节点返回给上一层，上一层节点通过判断其左右子树中是否恰好包含n1和n2两个节点，
 * 如果找到，对应的上一层节点肯定是所求的LCA。LCA节点对于上一层来说，他们要么一起处于左子树要么一起处于右子树，直接返回非空左右节点就是结果
 */
public class LowestCommonAncestorOfBinaryTree {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null || root.val == p.val || root.val == q.val) {
            return root;
        }

        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);

        if (left != null && right != null) {
            return root;
        }
        if (left != null) {
            return left;
        }
        return right;
    }
}
