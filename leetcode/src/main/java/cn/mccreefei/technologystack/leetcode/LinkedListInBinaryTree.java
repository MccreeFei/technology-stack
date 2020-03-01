package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2020-03-01 下午3:04
 * @refer <href>https://leetcode.com/problems/linked-list-in-binary-tree/</href>
 * @idea 递归 从每一个树节点往下遍历找连续的链表节点
 */
public class LinkedListInBinaryTree {
    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public boolean isSubPath(ListNode head, TreeNode root) {
        if (root == null) {
            return false;
        }
        return help(head, root) || isSubPath(head, root.left) || isSubPath(head, root.right);
    }

    /**
     * 从head开始是否出现连续的ListNode in TreeNode root
     */
    private boolean help(ListNode head, TreeNode root) {
        if (head == null) {
            return true;
        }
        if (root == null) {
            return false;
        }
        if (head.val != root.val) {
            return false;
        }
        return help(head.next, root.left) || help(head.next, root.right);
    }
}
