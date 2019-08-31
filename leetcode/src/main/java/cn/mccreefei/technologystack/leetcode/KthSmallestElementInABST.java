package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2019-08-31 上午9:54
 * @refer <href>https://leetcode.com/problems/kth-smallest-element-in-a-bst/</href>
 * @idea 根据二叉搜索树的性质 当左子树的数目==k-1则当前val为结果 小于k-1 则在右子树中查找 否则在左子树中查找
 * 另外一种思路使用链表存放中序遍历的节点，返回第k个val
 */
public class KthSmallestElementInABST {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }


    public int kthSmallest(TreeNode root, int k) {
        int leftCount = nodeCount(root.left);
        if (leftCount == k - 1) {
            return root.val;
        } else if (leftCount < k - 1) {
            return kthSmallest(root.right, k);
        } else {
            return kthSmallest(root.left, k - leftCount - 1);
        }
    }

    private int nodeCount(TreeNode node) {
        if (node == null) {
            return 0;
        }
        int leftCount = nodeCount(node.left);
        int rightCount = nodeCount(node.right);
        return leftCount + rightCount + 1;
    }


}
