package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2020-08-20 下午7:51
 * @refer <href>https://leetcode.com/problems/reorder-list/</href>
 * @idea 找中点 翻转中点后的链表 merge
 */
public class ReorderList {
    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public void reorderList(ListNode head) {
        if (head == null || head.next == null) {
            return;
        }
        ListNode mid = head, tail = head;
        //找中点
        while (tail.next != null && tail.next.next != null) {
            mid = mid.next;
            tail = tail.next.next;
        }
        //中点之后的链表翻转
        ListNode cur = mid.next;
        ListNode next = cur.next;
        cur.next = null;
        while (next != null) {
            ListNode tmp = next.next;
            next.next = cur;
            mid.next = next;
            cur = next;
            next = tmp;

        }
        //merge
        ListNode midNext = null;
        while (head != mid) {
            midNext = mid.next;
            mid.next = midNext.next;
            midNext.next = head.next;
            head.next = midNext;
            head = midNext.next;
        }

    }
}
