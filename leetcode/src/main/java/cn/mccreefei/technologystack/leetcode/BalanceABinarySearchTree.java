package cn.mccreefei.technologystack.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MccreeFei
 * @create 2020-03-16 上午9:15
 * @refer <href>https://leetcode.com/problems/balance-a-binary-search-tree/</href>
 * @idea 中序遍历 + 重新构造平衡二叉搜索树
 */
public class BalanceABinarySearchTree {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    class Solution {
        private List<Integer> nodes = new ArrayList<>();
        public TreeNode balanceBST(TreeNode root) {
            inOrderTravel(root);
            return buildBST(0, nodes.size() - 1);
        }
        private void inOrderTravel(TreeNode root) {
            if (root == null) {
                return;
            }
            inOrderTravel(root.left);
            nodes.add(root.val);
            inOrderTravel(root.right);
        }

        private TreeNode buildBST(int start, int end) {
            if (start > end) {
                return null;
            }
            int mid = start + (end - start)/2;
            TreeNode treeNode = new TreeNode(nodes.get(mid));
            treeNode.left = buildBST(start, mid - 1);
            treeNode.right = buildBST(mid + 1, end);
            return treeNode;
        }
    }
}
