package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2019-10-18 上午9:54
 * @refer <href>https://leetcode.com/problems/linked-list-cycle/</href>
 * @idea 快慢指针
 */
public class LinkedListCycle {
    class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    public boolean hasCycle(ListNode head) {
        if (head == null) {
            return false;
        }
        ListNode fast = head.next;
        ListNode slow = head;
        if (fast == slow) {
            return true;
        }
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                return true;
            }
        }
        return false;
    }
}
