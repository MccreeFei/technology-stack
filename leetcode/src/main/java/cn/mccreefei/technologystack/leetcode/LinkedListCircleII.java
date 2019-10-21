package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2019-10-21 上午11:06
 * x1:链表head -> circle head 距离  x2:circle head -> meet node 距离 x3:meet node -> circle head的距离
 * fast走过距离 ： x1 + x2 + x3 + x2  slow走过距离 ： x1 + x2
 * x1 + x2 + x3 + x2 == 2(x1 + x2) =>  x3 == x1
 */
public class LinkedListCircleII {
    class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    public ListNode detectCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
          if (fast == slow) {
              break;
          }
        }
        if (fast == null || fast.next == null) {
            return null;
        }
        slow = head;
        while (fast != slow) {
            fast = fast.next;
            slow = slow.next;
        }
        return slow;
    }
}
