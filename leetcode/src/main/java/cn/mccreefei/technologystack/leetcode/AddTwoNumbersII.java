package cn.mccreefei.technologystack.leetcode;

import java.util.LinkedList;

/**
 * @author MccreeFei
 * @create 2020-01-15 下午2:20
 * @refer <href>https://leetcode.com/problems/add-two-numbers-ii/</href>
 * @idea 使用栈
 */
public class AddTwoNumbersII {
    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        LinkedList<Integer> l1Stack = new LinkedList<>();
        LinkedList<Integer> l2Stack = new LinkedList<>();
        while (l1 != null || l2 != null) {
            if (l1 == null) {
                l2Stack.push(l2.val);
                l2 = l2.next;
            } else if (l2 == null) {
                l1Stack.push(l1.val);
                l1 = l1.next;
            } else {
                l1Stack.push(l1.val);
                l2Stack.push(l2.val);
                l1 = l1.next;
                l2 = l2.next;
            }
        }

        ListNode prev = null;
        int flag = 0;
        while (!l1Stack.isEmpty() || !l2Stack.isEmpty()) {
            int num1 = l1Stack.isEmpty() ? 0 : l1Stack.pop();
            int num2 = l2Stack.isEmpty() ? 0 : l2Stack.pop();
            ListNode cur = new ListNode((num1 + num2 + flag) % 10);
            flag = (num1 + num2 + flag) / 10;
            cur.next = prev;
            prev = cur;
        }
        if (flag != 0) {
            ListNode head = new ListNode(flag);
            head.next = prev;
            prev = head;
        }
        return prev;
    }
}
