package cn.mccreefei.technologystack.leetcode;

import java.util.Deque;
import java.util.LinkedList;

/**
 * @author MccreeFei
 * @create 2019-11-22 下午3:30
 * @refer <href>https://leetcode.com/problems/daily-temperatures/</href>
 * @idea 栈
 */
public class DailyTemperatures {
    public int[] dailyTemperatures(int[] T) {
        int[] result = new int[T.length];
        Deque<Integer> stack = new LinkedList<>();
        for (int i = 0; i < T.length; i++) {
            while (!stack.isEmpty() && T[stack.peek()] < T[i]) {
                final Integer index = stack.poll();
                result[index] = i - index;
            }
            stack.push(i);
        }
        return result;
    }
}
