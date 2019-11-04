package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2019-11-04 下午4:45
 * @refer <href>https://leetcode.com/problems/sort-list/</href>
 * @idea merge算法 找出中间节点分别对两端排序 合并
 */
public class SortList {
    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode prev = head;
        ListNode slow = head.next;
        ListNode fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            prev = prev.next;
        }
        prev.next = null;

        ListNode l1 = sortList(head);
        ListNode l2 = sortList(slow);

        return merge(l1, l2);

    }

    private ListNode merge(ListNode l1, ListNode l2) {
        ListNode soldier = new ListNode(1);
        ListNode cur = soldier;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                cur.next = l1;
                l1 = l1.next;
            } else {
                cur.next = l2;
                l2 = l2.next;
            }
            cur = cur.next;
        }
        while (l1 != null) {
            cur.next = l1;
            l1 = l1.next;
            cur = cur.next;
        }
        while (l2 != null) {
            cur.next = l2;
            l2 = l2.next;
            cur = cur.next;
        }
        return soldier.next;
    }
}
