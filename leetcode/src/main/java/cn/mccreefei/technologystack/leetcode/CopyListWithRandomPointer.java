package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2019-10-24 下午4:05
 * @refer <href>https://leetcode.com/problems/copy-list-with-random-pointer/</href>
 * @idea 三次遍历 第一次：每个原节点后面添加拷贝节点 第二次：根据源节点组织拷贝节点的random pointer 第三次：分别提取原链表与拷贝链表
 *       空间O(1), 时间O(3n)
 */
public class CopyListWithRandomPointer {
    class Node {
        public int val;
        public Node next;
        public Node random;

        public Node() {
        }

        public Node(int _val, Node _next, Node _random) {
            val = _val;
            next = _next;
            random = _random;
        }
    }

    public Node copyRandomList(Node head) {
        if (head == null) {
            return null;
        }
        /*
        每个原节点后面添加拷贝节点
         */
        Node iter = head;
        while (iter != null) {
            Node next = iter.next;
            iter.next = new Node(iter.val, next, null);
            iter = next;
        }
        /*
        根据源节点组织拷贝节点的random pointer
         */
        iter = head;
        while (iter != null) {
            if (iter.random != null) {
                iter.next.random = iter.random.next;
            }
            iter = iter.next.next;
        }
        /*
        分别提取原链表与拷贝链表
         */
        Node copyHead = head.next;
        Node headNext = head;
        Node copyNext = copyHead;
        iter = copyHead.next;
        Node copyIter;
        while (iter != null) {
            copyIter = iter.next;
            headNext.next = iter;
            copyNext.next = copyIter;
            headNext = headNext.next;
            copyNext = copyNext.next;
            iter = copyIter.next;
        }
        headNext.next = null;
        copyNext.next = null;
        return copyHead;
    }
}
