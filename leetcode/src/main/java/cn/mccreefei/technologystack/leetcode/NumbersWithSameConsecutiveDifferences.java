package cn.mccreefei.technologystack.leetcode;

import java.util.LinkedList;
import java.util.List;

/**
 * @author MccreeFei
 * @create 2020-08-18 下午4:29
 * @refer <href>https://leetcode.com/problems/numbers-with-same-consecutive-differences/</href>
 * @idea 回溯法
 */
public class NumbersWithSameConsecutiveDifferences {
    public int[] numsSameConsecDiff(int N, int K) {
        if (N == 1) {
            int[] res = new int[10];
            for (int i = 0; i < 10; i++) {
                res[i] = i;
            }
            return res;
        }
        List<Integer> res = new LinkedList<>();
        List<Integer> cur = new LinkedList<>();
        for (int i = 1; i < 10; i++) {
            cur.add(i);
            dfs(res, cur, N, K);
            cur.remove(cur.size() - 1);
        }
        return convert(res);

    }

    private void dfs(List<Integer> res, List<Integer> cur, int n,  int k) {
        if (cur.size() == n) {
            int num = 0;
            for (int c : cur) {
                num = num * 10 + c;
            }
            res.add(num);
            return;
        }
        int tmp = cur.get(cur.size() - 1);
        if (tmp + k <= 9) {
            cur.add(tmp + k);
            dfs(res, cur, n, k);
            cur.remove(cur.size() - 1);
        }
        //k != 0 防止 k==0时生成两次
        if (k != 0 && tmp - k >= 0) {
            cur.add(tmp - k);
            dfs(res, cur, n, k);
            cur.remove(cur.size() - 1);
        }
    }

    private int[] convert(List<Integer> list) {
        int[] res = new int[list.size()];
        int i = 0;
        for (int num : list) {
            res[i++] = num;
        }
        return res;
    }
}
