package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2020-08-09 下午12:46
 * @refer <href>https://leetcode.com/problems/find-kth-bit-in-nth-binary-string/</href>
 * @idea 递归
 */
public class FindKthBitInNthBinaryString {
    public char findKthBit(int n, int k) {
        if (n == 1) {
            return '0';
        }
        int len = (1 << n) - 1; //长度等于2的n次方-1
        if (k == len / 2 + 1) {
            return '1';
        } else if (k < len / 2 + 1) {
            return findKthBit(n - 1, k);
        } else {
            //取前一次的翻转 后 取反
            return findKthBit(n - 1, len - k + 1) == '0' ? '1' : '0';
        }
    }
}
