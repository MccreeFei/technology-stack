package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2019-11-19 下午5:33
 * @refer <href>https://leetcode.com/problems/merge-two-binary-trees/</href>
 * @idea 递归merge
 */
public class MergeTwoBinaryTrees {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        if (t1 == null) {
            return t2;
        }
        if (t2 == null) {
            return t1;
        }
        final TreeNode treeNode = new TreeNode(t1.val + t2.val);
        treeNode.left = mergeTrees(t1.left, t2.left);
        treeNode.right = mergeTrees(t1.right, t2.right);
        return treeNode;
    }
}
