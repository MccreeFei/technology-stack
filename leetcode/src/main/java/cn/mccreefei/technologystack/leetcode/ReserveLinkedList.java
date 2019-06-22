package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2019-06-21 下午5:49
 * @refer <href>https://leetcode.com/problems/reverse-linked-list/</href>
 * @idea 维护prev, cur, next三个指针，注意prev初始为null
 */
public class ReserveLinkedList {
    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode cur = head;
        ListNode next;
        while (cur != null) {
            next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }
        return prev;
    }
    class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    /**
     * 递归反转链表
     * @param head
     * @return
     */
    public ListNode recursiveReserveList(ListNode head) {
        return helpRecursive(null, head);
    }

    private ListNode helpRecursive(ListNode left, ListNode right) {
        if (right == null) {
            return left;
        }
        ListNode next = right.next;
        right.next = left;
        return helpRecursive(right, next);
    }

}
