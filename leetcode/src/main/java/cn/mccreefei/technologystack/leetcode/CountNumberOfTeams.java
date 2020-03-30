package cn.mccreefei.technologystack.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MccreeFei
 * @create 2020-03-30 下午4:36
 * @refer <href>https://leetcode.com/problems/count-number-of-teams/</href>
 * @idea dfs 回溯法
 */
public class CountNumberOfTeams {
    private int res = 0;
    public int numTeams(int[] rating) {
        List<Integer> incList = new ArrayList<>(3);
        help(rating, incList, 0, true);
        List<Integer> deList = new ArrayList<>(3);
        help(rating, deList, 0, false);
        return res;
    }

    private void help(int[] rating, List<Integer> lst, int start, boolean increase) {
        if (lst.size() == 3) {
            res++;
            return;
        }
        if (start > rating.length - 1) {
            return;
        }
        int prev = lst.size() == 0 ? increase ? 0 : Integer.MAX_VALUE : lst.get(lst.size() - 1);
        for (int i = start; i < rating.length; i++) {
            if ((increase && rating[i] > prev) || (!increase && rating[i] < prev)) {
                lst.add(rating[i]);
                help(rating, lst, i + 1, increase);
                lst.remove(lst.size() - 1);
            }
        }
    }

}
