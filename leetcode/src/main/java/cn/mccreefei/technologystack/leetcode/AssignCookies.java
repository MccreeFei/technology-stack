package cn.mccreefei.technologystack.leetcode;

import java.util.Arrays;

/**
 * @author MccreeFei
 * @create 2020-02-01 下午2:45
 * @refer <href>https://leetcode.com/problems/assign-cookies/</href>
 * @idea 贪心算法
 */
public class AssignCookies {
    public int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);
        int i = 0, j = 0;
        int res = 0;
        while (i < g.length && j < s.length) {
            if (s[j] >= g[i]) {
                i++;
                j++;
                res++;
            } else {
                j++;
            }
        }
        return res;
    }
}
