package cn.mccreefei.technologystack.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MccreeFei
 * @create 2020-05-24 上午10:06
 * @refer <href>https://leetcode.com/problems/interval-list-intersections/</href>
 * @idea 双指针
 */
public class IntervalListIntersections {
    public int[][] intervalIntersection(int[][] A, int[][] B) {
       int i = 0, j = 0;
        List<int[]> res = new ArrayList<>();
       while (i < A.length && j < B.length) {
           int left = Math.max(A[i][0], B[j][0]);
           int right = Math.min(A[i][1], B[j][1]);
           if (right >= left) {
               res.add(new int[]{left, right});
           }
           if (A[i][1] == right) {
               i++;
           } else {
               j++;
           }
       }
       return res.toArray(new int[res.size()][2]);
    }
}
