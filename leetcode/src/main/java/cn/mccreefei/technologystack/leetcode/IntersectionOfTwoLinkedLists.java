package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2019-10-22 上午11:10
 * @refer <href>https://leetcode.com/problems/intersection-of-two-linked-lists/</href>
 * @idea 保证a,b同时向后遍历, a==null时 a=headB; b==null时 b=headA;
 *       这样最终遍历的长度都是a+b,保证有交点时最多在第二次遍历时相遇。没有交点则在null相遇
 */
public class IntersectionOfTwoLinkedLists {
    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode a = headA;
        ListNode b = headB;
        if (a == null || b == null) {
            return null;
        }
        while (a != b) {
            a = a == null ? headB : a.next;
            b = b == null ? headA : b.next;
        }
        return a;
    }
}

