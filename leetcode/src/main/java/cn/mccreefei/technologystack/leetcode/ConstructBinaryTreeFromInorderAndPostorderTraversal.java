package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2020-07-27 下午10:53
 * @refer <href>https://leetcode.com/problems/construct-binary-tree-from-inorder-and-postorder-traversal/</href>
 * @idea 先找出postorder最后一个元素为root, 在inorder中找出root所在index i, 左子树个数leftNum就为 i-inStart+1,
 * 此时postorder中最边leftNum个数的元素就为左子树，其余右子树，分别递归构造成二叉树
 */
public class ConstructBinaryTreeFromInorderAndPostorderTraversal {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        int len = inorder.length;
        return help(inorder, 0, len - 1, postorder, 0, len - 1);
    }

    private TreeNode help(int[] inorder, int inStart, int inEnd, int[] postorder, int postStart, int postEnd) {
        if (inStart > inEnd || postStart > postEnd) {
            return null;
        }
        int rootVal = postorder[postEnd];
        TreeNode root = new TreeNode(rootVal);
        int i = inStart;
        for (; i <= inEnd; i++) {
            if (inorder[i] == rootVal) {
                break;
            }
        }
        int leftNum = i - inStart;
        root.left = help(inorder, inStart, i - 1, postorder, postStart, postStart + leftNum - 1);
        root.right = help(inorder, i + 1, inEnd, postorder, postStart + leftNum, postEnd - 1);
        return root;

    }
}
