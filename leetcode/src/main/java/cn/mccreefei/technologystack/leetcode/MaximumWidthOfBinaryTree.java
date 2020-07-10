package cn.mccreefei.technologystack.leetcode;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author MccreeFei
 * @create 2020-07-10 下午2:14
 * @refer <href>https://leetcode.com/problems/maximum-width-of-binary-tree/</href>
 * @idea 用HashMap给每个node都标上index 然后每层计算宽度
 */
public class MaximumWidthOfBinaryTree {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public int widthOfBinaryTree(TreeNode root) {
        if (root == null) {
            return 0;
        }
        Map<TreeNode, Integer> map = new HashMap<>();
        LinkedList<TreeNode> queue = new LinkedList<>();
        int max = 0;
        queue.push(root);
        map.put(root, 1);
        while (!queue.isEmpty()) {
            int size = queue.size();
            int start = 0, end = 0;
            for (int i = 0; i < size; i++) {
                TreeNode poll = queue.poll();
                int index = map.get(poll);
                if (i == 0) {
                    start = index;
                }
                if (i == size - 1) {
                    end = index;
                }
                if (poll.left != null) {
                    queue.offer(poll.left);
                    map.put(poll.left, index * 2 + 1);
                }
                if (poll.right != null) {
                    queue.offer(poll.right);
                    map.put(poll.right, index * 2 + 2);
                }
            }
            max = Math.max(max, end - start + 1);
        }
        return max;
    }
}
