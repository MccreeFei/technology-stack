package cn.mccreefei.technologystack.leetcode;

import java.util.LinkedList;
import java.util.List;

/**
 * @author MccreeFei
 * @create 2020-01-20 下午4:06
 * @refer <href>https://leetcode.com/problems/all-elements-in-two-binary-search-trees/</href>
 * @idea 中序遍历 然后merge
 */
public class AllElementsInTwoBinarySearchTrees {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public List<Integer> getAllElements(TreeNode root1, TreeNode root2) {
        List<Integer> l1 = new LinkedList<>();
        List<Integer> l2 = new LinkedList<>();
        help(root1, l1);
        help(root2, l2);
        List<Integer> result = new LinkedList<>();
        int i = 0, j= 0;
        while (i < l1.size() || j < l2.size()) {
            if (i >= l1.size()) {
                result.add(l2.get(j++));
            } else if (j >= l2.size()) {
                result.add(l1.get(i++));
            } else {
                result.add(l1.get(i) < l2.get(j) ? l1.get(i++) : l2.get(j++));
            }
        }
        return result;
    }

    private void help(TreeNode root, List<Integer> lst) {
        if (root == null) {
            return;
        }
        if (root.left != null) {
            help(root.left, lst);
        }
        lst.add(root.val);
        help(root.right, lst);
    }
}
