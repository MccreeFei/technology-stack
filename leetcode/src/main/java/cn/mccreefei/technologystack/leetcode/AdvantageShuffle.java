package cn.mccreefei.technologystack.leetcode;

import java.util.Arrays;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

/**
 * @author MccreeFei
 * @create 2020-01-19 上午11:30
 * @refer <href>https://leetcode.com/problems/advantage-shuffle/</href>
 * @idea 贪心算法 A排序 B按照val从大到小排序遍历 A的最大值>B.val则当前位置为A的最大值否则A的最小值
 */
public class AdvantageShuffle {
    public int[] advantageCount(int[] A, int[] B) {
        int[] result = new int[A.length];
        Arrays.sort(A);
        int left = 0;
        int right = A.length - 1;
        PriorityQueue<int[]> priorityQueue = new PriorityQueue<>((a, b) -> b[0] - a[0]);
        for (int i = 0; i < B.length; i++) {
            priorityQueue.offer(new int[]{B[i], i});
        }
        while (!priorityQueue.isEmpty()) {
            int[] poll = priorityQueue.poll();
            result[poll[1]] = A[right] > poll[0] ? A[right--] : A[left++];
        }
        return result;
    }

}
