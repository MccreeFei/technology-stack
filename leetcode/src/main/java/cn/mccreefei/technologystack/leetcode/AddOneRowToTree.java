package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2020-01-14 上午10:05
 * @refer <href>https://leetcode.com/problems/add-one-row-to-tree/</href>
 * @idea 递归 当d==1时插入节点
 */
public class AddOneRowToTree {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public TreeNode addOneRow(TreeNode root, int v, int d) {
        return help(root, true, v, d);
    }

    private TreeNode help(TreeNode node, boolean left, int v, int d) {
        if (d == 1) {
            TreeNode treeNode = new TreeNode(v);
            if (left) {
                treeNode.left = node;
            } else {
                treeNode.right = node;
            }
            return treeNode;
        }
        if (node == null || d < 1) {
            return node;
        }

        node.left = help(node.left, true, v, d - 1);
        node.right = help(node.right, false, v, d - 1);
        return node;
    }

}
