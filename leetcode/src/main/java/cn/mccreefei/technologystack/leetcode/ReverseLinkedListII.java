package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2019-08-04 下午4:33
 * @refer <href>https://leetcode.com/problems/reverse-linked-list-ii/</href>
 * @idea 维护好声明的指针
 */
public class ReverseLinkedListII {
    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public ListNode reverseBetween(ListNode head, int m, int n) {
        if (m == n || head.next == null) {
            return head;
        }

        ListNode cur = head;
        ListNode next = cur.next;
        //指向m前一个节点
        ListNode begin = null;
        //指向n后一个节点
        ListNode end = head;
        int curIndex = 1;
        //翻转链表后的尾节点
        ListNode newTail = null;
        //翻转链表后的头结点
        ListNode newHead = null;

        while (curIndex <= n) {
            end = end.next;
            if (curIndex < m) {
                if (curIndex == 1) {
                    begin = head;
                }else {
                    begin = begin.next;
                }
            } else {
                if (m == curIndex) {
                    newTail = cur;
                }
                cur.next = newHead;
                newHead = cur;
            }
            curIndex++;
            cur = next;
            if (next != null) {
                next = next.next;
            }
        }
        newTail.next = end;
        if (begin == null) {
            return newHead;
        } else {
            begin.next = newHead;
            return head;
        }
    }
}
