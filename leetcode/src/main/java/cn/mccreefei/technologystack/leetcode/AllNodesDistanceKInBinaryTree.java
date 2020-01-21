package cn.mccreefei.technologystack.leetcode;

import java.util.*;

/**
 * @author MccreeFei
 * @create 2020-01-21 下午7:23
 * @refer <href>https://leetcode.com/problems/all-nodes-distance-k-in-binary-tree/</href>
 * @idea 将二叉树转化为图
 */
public class AllNodesDistanceKInBinaryTree {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public List<Integer> distanceK(TreeNode root, TreeNode target, int K) {
        List<Integer> result = new ArrayList<>();
        Map<TreeNode, List<TreeNode>> graph = new HashMap<>();
        Set<TreeNode> visited = new HashSet<>();
        Queue<TreeNode> queue = new LinkedList<>();
        buildMap(graph, root, null);
        queue.add(target);
        visited.add(target);
        while (!queue.isEmpty()) {
            int size = queue.size();
            if (K == 0) {
                for (int i = 0; i < size; i++) {
                    result.add(queue.poll().val);
                }
                return result;
            }
            for (int i = 0; i < size; i++) {
                TreeNode poll = queue.poll();
                for (TreeNode node : graph.get(poll)) {
                    if (visited.contains(node)) {
                        continue;
                    }
                    visited.add(node);
                    queue.add(node);
                }
            }
            K--;
        }
        return result;

    }

    private void buildMap(Map<TreeNode, List<TreeNode>> graph, TreeNode root, TreeNode parent) {
        if (root == null) {
            return;
        }
        if (!graph.containsKey(root)) {
            graph.put(root, new ArrayList<>());
        }
        if (parent != null) {
            graph.get(root).add(parent);
            graph.get(parent).add(root);
        }
        buildMap(graph, root.left, root);
        buildMap(graph, root.right, root);
    }
}
