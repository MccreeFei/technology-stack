package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2019-08-02 上午9:48
 * @refer <href>https://leetcode.com/problems/partition-list/</href>
 * @idea 使用两个哨兵节点
 */
public class PartitionList {
    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public ListNode partition(ListNode head, int x) {
        ListNode beforeSoldier = new ListNode(1);
        ListNode afterSoldier = new ListNode(1);
        ListNode beforeIndex = beforeSoldier;
        ListNode afterIndex = afterSoldier;

        while (head != null) {
            if (head.val < x) {
                beforeIndex.next = head;
                beforeIndex = beforeIndex.next;
            } else {
                afterIndex.next = head;
                afterIndex = afterIndex.next;
            }
            head = head.next;
        }
        afterIndex.next = null;
        beforeIndex.next = afterSoldier.next;
        return beforeSoldier.next;
    }
}
