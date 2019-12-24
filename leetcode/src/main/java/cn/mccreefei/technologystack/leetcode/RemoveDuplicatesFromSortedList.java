package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2019-12-24 下午3:36
 * @refer <href>https://leetcode.com/problems/remove-duplicates-from-sorted-list/</href>
 * @idea 链表操作
 */
public class RemoveDuplicatesFromSortedList {
    /**
     * Definition for singly-linked list.
     */
    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public ListNode deleteDuplicates(ListNode head) {
        ListNode prev = head;
        while (prev != null && prev.next != null) {
            ListNode cur = prev.next;
            while (cur != null && cur.val == prev.val) {
                cur = cur.next;
            }
            prev.next = cur;
            prev = cur;
        }
        return head;
    }

}
