package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2020-06-20 下午5:25
 * @refer <href>https://leetcode.com/problems/permutation-sequence/</href>
 * @idea 找规律
 */
public class PermutationSequence {
    public String getPermutation(int n, int k) {
        int[] pos = new int[10];
        for (int i = 0; i < 10; i++) {
            pos[i] = i + 1;
        }
        int acc = 1;
        k = k - 1;
        for (int i = 2; i < n; i++) {
            acc *= i;
        }
        n = n - 1;
        StringBuilder sb = new StringBuilder();
        while (n > 0) {
            sb.append(pop(pos, k / acc));
            k = k % acc;
            acc = acc / n;
            n--;
        }
        sb.append(pos[0]);
        return sb.toString();
    }

    /**
     * 弹出index位置上的数
     */
    private int pop(int[] pos, int index) {
        int res = pos[index];
        for (int i = index; i < 9; i++) {
            pos[i] = pos[i + 1];
        }
        return res;
    }
}
