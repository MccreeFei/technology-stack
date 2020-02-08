package cn.mccreefei.technologystack.leetcode;

import java.util.LinkedList;

/**
 * @author MccreeFei
 * @create 2020-02-08 上午10:54
 * @refer <href>https://leetcode.com/problems/binary-search-tree-iterator/</href>
 * @idea 中序遍历 + 队列
 */
public class BSTIterator {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }
    private LinkedList<Integer> queue;
    public BSTIterator(TreeNode root) {
        queue = new LinkedList<>();
        inOrderTravel(root);
    }

    private void inOrderTravel(TreeNode root) {
        if (root == null) {
            return;
        }
        inOrderTravel(root.left);
        queue.offer(root.val);
        inOrderTravel(root.right);
    }

    /**
     * @return the next smallest number
     */
    public int next() {
        return queue.poll();
    }

    /**
     * @return whether we have a next smallest number
     */
    public boolean hasNext() {
        return !queue.isEmpty();
    }
}
