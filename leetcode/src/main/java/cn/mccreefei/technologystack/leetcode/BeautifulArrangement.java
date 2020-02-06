package cn.mccreefei.technologystack.leetcode;

/**
 * @author MccreeFei
 * @create 2020-02-06 下午7:54
 * @refer <href>https://leetcode.com/problems/beautiful-arrangement/</href>
 * @idea 回溯法
 */
public class BeautifulArrangement {
    private int count = 0;

    public int countArrangement(int N) {
        int[] used = new int[N + 1];
        help(N, 1, used);
        return count;
    }

    /**
     *
     * @param n 多少个数
     * @param pos 当前位置
     * @param used 当前所有数的使用情况 EXP: used[1] = 1代表1已被使用 used[2] = 0代表2未被使用
     */
    private void help(int n, int pos, int[] used) {
        if (pos > n) {
            count++;
            return;
        }
        for (int i = 1; i <= n; i++) {
            if (used[i] == 0 && (i % pos == 0 || pos % i == 0)) {
                used[i] = 1;
                help(n, pos + 1, used);
                used[i] = 0;
            }
        }
    }
}
