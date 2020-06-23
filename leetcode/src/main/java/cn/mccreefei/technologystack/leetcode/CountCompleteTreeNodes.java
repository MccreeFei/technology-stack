package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2020-06-23 下午5:56
 * @refer <href>https://leetcode.com/problems/count-complete-tree-nodes/</href>
 * @idea 递归 分别对node计算最左边和最右边的高度 相等则为完美二叉树节点数等于2^h - 1 否则递归左右子树 + 1
 */
public class CountCompleteTreeNodes {
    public int countNodes(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int left = 0, right = 0;
        TreeNode leftNode = root;
        TreeNode rightNode = root;
        while (leftNode != null) {
            left++;
            leftNode = leftNode.left;
        }
        while (rightNode != null) {
            right++;
            rightNode = rightNode.right;
        }
        if (left == right) {
            return (1 << left) - 1;
        }
        return countNodes(root.left) + countNodes(root.right) + 1;
    }
}
