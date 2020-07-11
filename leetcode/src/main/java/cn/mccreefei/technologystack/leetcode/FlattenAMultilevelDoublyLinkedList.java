package cn.mccreefei.technologystack.leetcode;

import java.util.LinkedList;

/**
 * @author MccreeFei
 * @create 2020-07-11 上午11:27
 * @refer <href>https://leetcode.com/problems/flatten-a-multilevel-doubly-linked-list/</href>
 * @idea 栈
 */
public class FlattenAMultilevelDoublyLinkedList {
    class Node {
        public int val;
        public Node prev;
        public Node next;
        public Node child;
    };
    public Node flatten(Node head) {
        LinkedList<Node> stack = new LinkedList<>();
        if (head == null) {
            return null;
        }
        Node prev = new Node();
        Node cur = head;
        while (cur != null) {
            prev.child = null;
            prev.next = cur;
            cur.prev = prev;
            prev = cur;
            if (cur.child != null) {
                if (cur.next != null) {
                    stack.push(cur.next);
                }
                cur = cur.child;
            } else {
                cur = cur.next;
            }
            if (cur == null && !stack.isEmpty()) {
                cur = stack.pop();
            }
        }
        prev.next = null;
        prev.child = null;
        head.prev = null;
        return head;
    }
}
