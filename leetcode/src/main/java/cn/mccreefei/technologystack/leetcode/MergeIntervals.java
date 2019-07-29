package cn.mccreefei.technologystack.leetcode;

import java.util.Arrays;

/**
 * @author MccreeFei
 * @create 2019-07-29 下午5:45
 * @refer <href>https://leetcode.com/problems/merge-intervals/</href>
 * @idea 先排序 后比较有无交叉
 */
public class MergeIntervals {
    public int[][] merge(int[][] intervals) {
        if (intervals == null || intervals.length < 2) {
            return intervals;
        }
        sort(intervals);
        int prev = 0;
        for (int i = 1; i < intervals.length; i++) {
            //有交叉
            if (intervals[i][0] <= intervals[prev][intervals[prev].length-1]) {
                //找出最大值 合并交叉
                int[] temp = new int[2];
                temp[0] = intervals[prev][0];
                int m = intervals[prev][intervals[prev].length-1];
                int n = intervals[i][intervals[i].length-1];
                temp[1] = Math.max(m, n);
                intervals[prev] = temp;
            } else {
                //无交叉 记录该项目 prev++
                intervals[++prev] = intervals[i];
            }
        }
        int[][] result = new int[prev+1][];
        System.arraycopy(intervals, 0, result, 0, prev+1);
        return result;
    }

    /**
     * 冒泡排序
     */
    private void sort(int[][] data) {
        for (int i = 0; i < data.length; i++) {
            boolean flag = false;
            for (int j = data.length - 1; j > i; j--) {
                if (data[j][0] < data[j-1][0]) {
                    swap(data, j, j-1);
                    flag = true;
                }
            }
            if (!flag) {
                break;
            }
        }
    }

    private void swap(int[][] data, int i, int j) {
        int[] temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

}
