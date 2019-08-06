package cn.mccreefei.technologystack.leetcode;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author MccreeFei
 * @create 2019-08-06 下午5:30
 * @refer <href>https://leetcode.com/problems/binary-tree-zigzag-level-order-traversal/</href>
 * @idea 借助队列 和一个辅助暂存列表 完成层级遍历 通过leftOrder控制顺序
  */
public class BinaryTreeZigzagLevelOrderTraversal {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> result = new LinkedList<>();
        if (root == null) {
            return result;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        List<TreeNode> tempList = new LinkedList<>();
        queue.offer(root);
        boolean leftOrder = true;
        while (!queue.isEmpty()) {
            LinkedList<Integer> lst = new LinkedList<>();
            result.add(lst);
            while (!queue.isEmpty()) {
                TreeNode treeNode = queue.poll();
                tempList.add(treeNode);
            }
            for (TreeNode node : tempList) {
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
                if (leftOrder) {
                    lst.add(node.val);
                } else {
                    lst.addFirst(node.val);
                }
            }
            leftOrder = !leftOrder;
            tempList.clear();
        }
        return result;
    }
}
