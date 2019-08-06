package cn.mccreefei.technologystack.leetcode;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author MccreeFei
 * @create 2019-08-06 上午9:50
 * @refer <href>https://leetcode.com/problems/binary-tree-level-order-traversal/</href>
 * @idea 借助队列 和一个辅助暂存列表 完成层级遍历
 */
public class BinaryTreeLevelOrderTraversal {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new LinkedList<>();
        if (root == null) {
            return result;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        List<TreeNode> tempList = new LinkedList<>();
        while (!queue.isEmpty()) {
            List<Integer> lst = new LinkedList<>();
            while (!queue.isEmpty()) {
                TreeNode treeNode = queue.poll();
                tempList.add(treeNode);
            }
            for (TreeNode node : tempList) {
                lst.add(node.val);
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            result.add(lst);
            tempList.clear();
        }
        return result;
    }
}
