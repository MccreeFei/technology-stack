package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2019-07-15 下午4:06
 * @refer <href>https://leetcode.com/problems/swap-nodes-in-pairs/</href>
 * @idea 设置一个假的dummy节点，next指向head，first与second互换位置，dummy next指向second 循环往后
 */
public class SwapNodesInPairs {
    public ListNode swapPairs(ListNode head) {
        ListNode dummy = new ListNode(1);
        dummy.next = head;
        ListNode first = head;
        ListNode current = dummy;
        while (first != null && first.next != null) {
            ListNode second = first.next;
            first.next = second.next;
            second.next = first;
            current.next = second;
            current = current.next.next;
            first = first.next;
        }
        return dummy.next;

    }

    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }
}

