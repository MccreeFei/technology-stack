package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2019-10-17 下午7:49
 * @refer <href>https://leetcode.com/problems/flatten-binary-tree-to-linked-list/</href>
 * @idea 先遍历右子树 后遍历左子树 将节点的左节点置为null，右节点置为prev,prev=root
 */
public class FlattenBinaryTreeToLinkedList {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    private TreeNode prev = null;

    public void flatten(TreeNode root) {
        if (root == null) {
            return;
        }
        flatten(root.right);
        flatten(root.left);
        root.left = null;
        root.right = prev;
        prev = root;
    }
}
