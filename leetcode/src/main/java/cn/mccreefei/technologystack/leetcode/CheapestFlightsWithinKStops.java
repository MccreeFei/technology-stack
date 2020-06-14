package cn.mccreefei.technologystack.leetcode;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author MccreeFei
 * @create 2020-06-14 下午5:38
 * @refer <href>https://leetcode.com/problems/cheapest-flights-within-k-stops/</href>
 * @idea dfs + 剪枝
 */
public class CheapestFlightsWithinKStops {
    private int min = Integer.MAX_VALUE;
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int K) {
        /**
         * key : dst place
         * value : 所有到达dst place的列表 Pair: key:src value:cost
         */
        Map<Integer, List<Pair<Integer, Integer>>> map = new HashMap<>();
        for (int[] flight : flights) {
            List<Pair<Integer, Integer>> lst = map.computeIfAbsent(flight[1], k -> new ArrayList<>());
            lst.add(new Pair<>(flight[0], flight[2]));
        }
        help(src, dst, K, map, 0);
        return min == Integer.MAX_VALUE ? -1 : min;
    }

    private void  help(int src, int dst, int k, Map<Integer, List<Pair<Integer, Integer>>> map, int costs) {
        /**
         * 剪枝 途径stops超标或者costs已经超出最小值
         */
        if (k < 0 || costs >= min) {
            return;
        }
        List<Pair<Integer, Integer>> lst = map.get(dst);
        if (lst == null) {
            return;
        }
        for (Pair<Integer, Integer> pair : lst) {
            if (pair.getKey().equals(src)) {
                min = Math.min(min, costs + pair.getValue());
            } else {
                help(src, pair.getKey(), k - 1, map, costs + pair.getValue());
            }
        }
    }
}
