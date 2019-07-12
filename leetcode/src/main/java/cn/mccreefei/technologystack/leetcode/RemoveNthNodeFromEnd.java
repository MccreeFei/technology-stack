package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2019-07-12 下午1:35
 * @refer <href>https://leetcode.com/problems/remove-nth-node-from-end-of-list/</href>
 * @idea end和toDelete指针相隔n，同时向后遍历，当end触尾，toDelete就是要删除的节点。维护一个toDelete的prev指针，用来链表删除
 */
public class RemoveNthNodeFromEnd {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode prev = head;
        ListNode toDelete = head;
        ListNode end = head;
        //使得toDelete与end相隔n
        while (n-- > 0) {
            end = end.next;
        }
        boolean first = true;
        while (end != null) {
             end = end.next;
             toDelete = toDelete.next;
             //第一次prev不需移动
             if (first) {
                 first = false;
             }else {
                 prev = prev.next;
             }
        }
        //prev == toDelete 表明删除的是第一个节点 返回next既可
        if (prev == toDelete) {
            return toDelete.next;
        }
        prev.next = toDelete.next;
        return head;
    }

    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }
}
