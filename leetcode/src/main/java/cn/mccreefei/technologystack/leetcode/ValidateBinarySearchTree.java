package cn.mccreefei.technologystack.leetcode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author MccreeFei
 * @create 2019-08-05 上午9:50
 * @refer <href>https://leetcode.com/problems/validate-binary-search-tree/</href>
 * @idea 先中序遍历 再判断顺序
 */
public class ValidateBinarySearchTree {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public boolean isValidBST(TreeNode root) {
        List<Integer> inOrderList = new ArrayList<>();
        inOrder(root, inOrderList);
        if (inOrderList.size() < 2) {
            return true;
        }
        int prev = inOrderList.get(0);
        for (int i = 1; i < inOrderList.size(); i++) {
            int cur = inOrderList.get(i);
            if (cur <= prev) {
                return false;
            }
            prev = cur;
        }
        return true;
    }

    private void inOrder(TreeNode root, List<Integer> inOrderList) {
        if (root == null) {
            return;
        }
        inOrder(root.left, inOrderList);
        inOrderList.add(root.val);
        inOrder(root.right, inOrderList);
    }

}
