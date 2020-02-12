package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2020-02-12 上午10:02
 * @refer <href>https://leetcode.com/problems/binary-tree-coloring-game/</href>
 * @idea 分别计算xNode的左子树、右子树、祖先连接出去的node数量，任意一个方向延伸出去的node数量大于n/2，就保证能赢
 */
public class BinaryTreeColoringGame {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }


    public boolean btreeGameWinningMove(TreeNode root, int n, int x) {
        TreeNode xNode = findX(root, x);
        int leftCount = count(xNode.left);
        int rightCount = count(xNode.right);
        int parentCount = n - leftCount - rightCount - 1;
        return leftCount > n / 2 || rightCount > n / 2 || parentCount > n / 2;
    }

    private TreeNode findX(TreeNode root, int x) {
        if (root == null || root.val == x) {
            return root;
        }
        TreeNode left = findX(root.left, x);
        return left != null ? left : findX(root.right, x);
    }

    private int count(TreeNode xNode) {
        if (xNode == null) {
            return 0;
        }
        return 1 + count(xNode.left) + count(xNode.right);
    }

}
