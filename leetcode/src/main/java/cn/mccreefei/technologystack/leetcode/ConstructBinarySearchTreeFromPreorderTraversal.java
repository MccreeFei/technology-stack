package cn.mccreefei.technologystack.leetcode;

import java.util.LinkedList;

/**
 * @author MccreeFei
 * @create 2020-04-20 下午3:38
 * @refer <href>https://leetcode.com/problems/construct-binary-search-tree-from-preorder-traversal/</href>
 * @idea 栈 记录前一个节点prev 当cur.val < prev.val,则将prev推入栈中，prev = cur,
 *       cur.val > prev.val 则利用栈找出最早小于cur.val的node node.right = cur, prev = node
 */
public class ConstructBinarySearchTreeFromPreorderTraversal {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public TreeNode bstFromPreorder(int[] preorder) {
        if (preorder == null || preorder.length == 0) {
            return null;
        }
        TreeNode root = new TreeNode(preorder[0]);
        LinkedList<TreeNode> stack = new LinkedList<>();
        TreeNode prev = root;
        for (int i = 1; i < preorder.length; i++) {
            TreeNode node = new TreeNode(preorder[i]);
            if (node.val < prev.val) {
                stack.push(prev);
                prev.left = node;
            } else {
                while(!stack.isEmpty() && node.val > stack.peek().val) {
                    prev = stack.pop();
                }
                prev.right = node;
            }
            prev = node;
        }
        return root;
    }
}
