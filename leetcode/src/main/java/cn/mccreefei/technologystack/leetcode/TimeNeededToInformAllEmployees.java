package cn.mccreefei.technologystack.leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author MccreeFei
 * @create 2020-03-08 上午10:22
 * @refer <href>https://leetcode.com/problems/time-needed-to-inform-all-employees/</href>
 * @idea dfs 题意：从根节点到叶子节点的所有路径花费时间的最大值
 */
public class TimeNeededToInformAllEmployees {
    class TreeNode {
        int id;
        int informTime;
        List<TreeNode> children;
        TreeNode(int id, int informTime) {
            this.id = id;
            this.informTime = informTime;
        }

        void addChild(TreeNode node) {
            if (children == null) {
                children = new ArrayList<>();
            }
            children.add(node);
        }

        boolean hasChild() {
            return children != null && children.size() > 0;
        }
    }
    private int maxTime = 0;
    public int numOfMinutes(int n, int headID, int[] manager, int[] informTime) {
        TreeNode root = null;
        TreeNode[] nodes = new TreeNode[n];
        int result = 0;
        LinkedList<TreeNode> queue = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            nodes[i] = new TreeNode(i, informTime[i]);
        }
        for (int i = 0; i < n; i++) {
            if (manager[i] == -1) {
                root = nodes[i];
                continue;
            }
            nodes[manager[i]].addChild(nodes[i]);
        }
        dfs(root, 0);
        return maxTime;
    }

    private void dfs(TreeNode root, int time) {
        if (root == null || !root.hasChild()) {
            maxTime = Math.max(maxTime, time);
            return;
        }
        for (TreeNode child : root.children) {
            dfs(child, time + root.informTime);
        }
    }
}
