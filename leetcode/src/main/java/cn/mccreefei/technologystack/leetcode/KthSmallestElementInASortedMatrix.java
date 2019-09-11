package cn.mccreefei.technologystack.leetcode;

import java.util.PriorityQueue;

/**
 * @author MccreeFei
 * @create 2019-09-11 上午11:20
 * @refer <href>https://leetcode.com/problems/kth-smallest-element-in-a-sorted-matrix/</href>
 * @idea 最大堆实现
 */
public class KthSmallestElementInASortedMatrix {
    public int kthSmallest(int[][] matrix, int k) {
        PriorityQueue<Integer> queue = new PriorityQueue<>((a,b)-> b - a);
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                queue.offer(matrix[i][j]);
                if (queue.size() > k) {
                    queue.poll();
                }
            }
        }
        return queue.poll();
    }
}
