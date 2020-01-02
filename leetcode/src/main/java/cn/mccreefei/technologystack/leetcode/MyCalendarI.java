package cn.mccreefei.technologystack.leetcode;

import java.util.TreeMap;

/**
 * @author MccreeFei
 * @create 2020-01-02 下午3:14
 * @refer <href>https://leetcode.com/problems/my-calendar-i/</href>
 * @idea TreeMap(start->end) 确保lowerKey的end小于等于当前start即可
 */
public class MyCalendarI {
    private TreeMap<Integer, Integer> treeMap;
    public MyCalendarI() {
        treeMap = new TreeMap<>();
    }

    public boolean book(int start, int end) {
        Integer lowerKey = treeMap.lowerKey(end);
        if (lowerKey == null || treeMap.get(lowerKey) <= start) {
            treeMap.put(start, end);
            return true;
        } else {
            return false;
        }
    }
}
