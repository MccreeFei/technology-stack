package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2019-06-29 上午10:57
 * @refer <href>https://leetcode.com/problems/path-sum-iii/</href>
 * <idea> 递归计算从当前节点开始的路径为多少  再递归左右子树</idea>
 */
public class PathSumIII {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public int pathSum(TreeNode root, int sum) {
        if (root == null) {
            return 0;
        }
        int fromLeft = pathSum(root.left, sum);
        int fromRight = pathSum(root.right, sum);
        return fromLeft + fromRight + fromRoot(root, sum);
    }

    /**
     * 从该节点向下有多少条路径
     * @param root
     * @param sum
     * @return
     */
    private int fromRoot(TreeNode root, int sum) {
        if (root == null) {
            return 0;
        }
        int left = fromRoot(root.left,sum-root.val);
        int right = fromRoot(root.right, sum-root.val);
        return left + right + (sum-root.val == 0 ? 1 : 0);
    }

}
