package cn.mccreefei.technologystack.leetcode;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author MccreeFei
 * @create 2020-02-16 下午3:08
 * @refer <href>https://leetcode.com/problems/maximum-number-of-events-that-can-be-attended/</href>
 * @idea 贪心 对于每个事件按照结束事件从下到大排序 每次选一个
 */
public class MaximumNumberOfEventsThatCanBeAttended {
    public int maxEvents(int[][] events) {
        Arrays.sort(events, (a, b) -> a[1] == b[1] ? a[0] - b[0] : a[1] - b[1]);
        Set<Integer> daySet = new HashSet<>();
        for (int[] event : events) {
            if (event[0] == event[1]) {
                daySet.add(event[0]);
            } else {
                for (int i = event[0]; i <= event[1]; i++) {
                    if (daySet.add(i)) {
                        break;
                    }
                }
            }
        }
        return daySet.size();
    }
}
