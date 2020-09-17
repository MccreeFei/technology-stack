package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2020-09-17 上午11:51
 * @refer <href>https://leetcode.com/problems/maximum-xor-of-two-numbers-in-an-array/</href>
 * @idea 字典树 保存每个数在字典树上bit的可能 遍历每个num计算与当前num的最大异或值 计算方式:从高位开始在字典树中查找异或位置的数，有就加上
 */
public class MaximumXOROfTwoNumbersInAnArray {
    public int findMaximumXOR(int[] nums) {
        int max = 0;
        TrieNode root = new TrieNode();
        for (int num : nums) {
            TrieNode cur = root;
            TrieNode other = root;
            int value = 0;
            for (int i = 31; i >=0; i--) {
                int bit = (num >>> i) & 1;
                if (cur.children[bit] == null) {
                    cur.children[bit] = new TrieNode();
                }
                cur = cur.children[bit];
                if (other.children[1 - bit] != null) {
                    value += (1 << i);
                    other = other.children[1 - bit];
                } else {
                    other = other.children[bit];
                }
            }
            max = Math.max(max, value);
        }
        return max;
    }

    class TrieNode {
        TrieNode[] children;
        TrieNode() {
            children = new TrieNode[2];
        }
    }
}
