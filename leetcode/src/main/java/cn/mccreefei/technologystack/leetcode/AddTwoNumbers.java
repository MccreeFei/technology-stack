package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2019-07-01 下午2:08
 * @refer <href>https://leetcode.com/problems/add-two-numbers/</href>
 * @idea 双链表同时向后 加上flag值
 */
public class AddTwoNumbers {
    class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int flag = 0;
        ListNode result = null;
        ListNode tail = null;
        while (l1 != null || l2 != null) {
            int num1 = 0;
            int num2 = 0;
            if (l1 != null) {
                num1 = l1.val;
                l1 = l1.next;
            }
            if (l2 != null) {
                num2 = l2.val;
                l2 = l2.next;
            }
            int tmp = num1 + num2 + flag;
            if (tail == null) {
                result = new ListNode(tmp % 10);
                tail = result;
            } else {
                tail.next = new ListNode(tmp % 10);
                tail = tail.next;
            }
            flag = tmp / 10;
        }
        if (flag != 0) {
            tail.next = new ListNode(flag);
        }
        return result;
    }
}
