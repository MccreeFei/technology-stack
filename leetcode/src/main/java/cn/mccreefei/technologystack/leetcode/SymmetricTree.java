package cn.mccreefei.technologystack.leetcode;

import java.util.LinkedList;

/**
 * @author MccreeFei
 * @create 2019-10-16 下午4:21
 * @refer <href>https://leetcode.com/problems/symmetric-tree/</href>
 * @idea 辅助LinkedList实现
 */
public class SymmetricTree {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public boolean isSymmetric(TreeNode root) {
        LinkedList<TreeNode> list = new LinkedList<>();
        LinkedList<TreeNode> tmpList = new LinkedList<>();
        LinkedList<TreeNode> refer;
        list.add(root);
        while (!list.isEmpty()) {
            int start = 0;
            int end = list.size() - 1;
            while (start <= end) {
                if (!compare(list.get(start), list.get(end))) {
                    return false;
                }
                start++;
                end--;
            }
            while (!list.isEmpty()) {
                TreeNode poll = list.pollFirst();
                if (poll == null) {
                    continue;
                }
                tmpList.add(poll.left);
                tmpList.add(poll.right);
            }
            refer = list;
            list = tmpList;
            tmpList = refer;
        }
        return true;
    }

    private boolean compare(TreeNode node1, TreeNode node2) {
        if (node1 == null && node2 == null) {
            return true;
        }
        if (node1 == null || node2 == null) {
            return false;
        }
        return node1.val == node2.val;
    }

}
