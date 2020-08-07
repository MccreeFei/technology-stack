package cn.mccreefei.technologystack.leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @author MccreeFei
 * @create 2020-08-07 下午4:11
 * @refer <href>https://leetcode.com/problems/vertical-order-traversal-of-a-binary-tree/</href>
 * @idea dfs + 优先队列
 */
public class VerticalOrderTraversalOfABinaryTree {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public class Point {
        int x;
        int y;
        int val;

        Point(int x, int y, int val) {
            this.x = x;
            this.y = y;
            this.val = val;
        }
    }

    public List<List<Integer>> verticalTraversal(TreeNode root) {
        PriorityQueue<Point> priorityQueue = new PriorityQueue<Point>((a, b) -> {
            if (a.x != b.x) {
                return a.x - b.x;
            }
            if (a.y != b.y) {
                return b.y - a.y;
            }
            return a.val - b.val;
        });
        dfs(root, 0, 0, priorityQueue);
        List<List<Integer>> result = new LinkedList<>();
        int prevX = Integer.MAX_VALUE;
        while (!priorityQueue.isEmpty()) {
            Point point = priorityQueue.poll();
            if (point.x == prevX) {
                result.get(result.size() - 1).add(point.val);
            } else {
                List<Integer> lst = new LinkedList<>();
                result.add(lst);
                lst.add(point.val);
            }
            prevX = point.x;
        }
        return result;
    }

    private void dfs(TreeNode node, int x, int y, PriorityQueue<Point> queue) {
        if (node == null) {
            return;
        }
        queue.offer(new Point(x, y, node.val));
        dfs(node.left, x - 1, y - 1, queue);
        dfs(node.right, x + 1, y - 1, queue);
    }
}
