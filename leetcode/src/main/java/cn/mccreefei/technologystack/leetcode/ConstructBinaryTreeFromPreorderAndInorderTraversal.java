package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2019-10-17 上午11:07
 * @refer <href>https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/</href>
 * @idea 先序遍历第一个确定root,在中序遍历中查找root值,左边值在左子树,右边值在右子树,递归生成树
 */
public class ConstructBinaryTreeFromPreorderAndInorderTraversal {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        return help(preorder, inorder, 0, preorder.length - 1, 0, inorder.length - 1);
    }

    private TreeNode help(int[] preorder, int[] inorder, int preStart, int preEnd, int inStart, int inEnd) {
        if (preEnd < preStart || inEnd < inStart) {
            return null;
        }
        int val = preorder[preStart];
        TreeNode root = new TreeNode(val);
        /**
         * 确定root值在中序数组中的位置
         */
        int rootInOrderIndex = inStart;
        while (inorder[rootInOrderIndex] != val) {
            rootInOrderIndex++;
        }
        /**
         * root左子树节点的数量
         */
        int leftCount = rootInOrderIndex - inStart;
        root.left = help(preorder, inorder, preStart +  1,
                preStart + leftCount, inStart, rootInOrderIndex - 1);
        root.right = help(preorder, inorder, preStart + leftCount + 1,
                preEnd, rootInOrderIndex + 1, inEnd);
        return root;
    }
}
