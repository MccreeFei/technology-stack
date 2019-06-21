package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2019-06-21 下午5:19
 * @refer <href>https://leetcode.com/problems/remove-linked-list-elements/</href>
 * @idea 维护prev 与 next两个指针即可
 */
public class RemoveLinkedListElements {
    public ListNode removeElements(ListNode head, int val) {
        while (head != null && head.val == val) {
            head = head.next;
        }
        if (head == null) {
            return null;
        }
        ListNode next = head.next;
        ListNode prev = head;
        while (next != null) {
            if (next.val == val) {
                prev.next = next.next;
            } else {
                prev = next;
            }
            next = next.next;
        }
        return head;
    }
    class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }
}


