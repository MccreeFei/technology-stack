package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2019-09-07 上午10:59
 * @refer <href>https://leetcode.com/problems/odd-even-linked-list/</href>
 * @idea 分别记录odd与even链表，最后拼接
 */
public class OddEvenLinkedList {
    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public ListNode oddEvenList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode evenHead = head.next;
        ListNode even = head.next;
        ListNode odd = head;
        ListNode current = head.next.next;
        boolean isOdd = true;
        while (current != null) {
            if (isOdd) {
                odd.next = current;
                odd = odd.next;
            } else {
                even.next = current;
                even = even.next;
            }
            isOdd = !isOdd;
            current = current.next;
        }
        even.next = null;
        odd.next = evenHead;
        return head;
    }
}
