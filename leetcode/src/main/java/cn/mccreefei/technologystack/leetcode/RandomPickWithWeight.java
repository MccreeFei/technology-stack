package cn.mccreefei.technologystack.leetcode;

import java.util.Random;

/**
 * @author MccreeFei
 * @create 2020-06-06 上午11:28
 * @refer <href>https://leetcode.com/problems/random-pick-with-weight/</href>
 * @idea 累加 二分查找
 */
public class RandomPickWithWeight {
    private int[] data;
    private Random random = new Random();
    public RandomPickWithWeight(int[] w) {
        data = new int[w.length];
        int prev = 0;
        for (int i = 0; i < w.length; i++) {
            data[i] = prev + w[i];
            prev = data[i];
        }
    }

    public int pickIndex() {
        int target = random.nextInt(data[data.length - 1]) + 1;
        int start = 0, end = data.length - 1;
        while (start < end) {
            int mid = start + (end - start)/2;
            if (data[mid] == target) {
                return mid;
            }
            if (data[mid] < target) {
                start = mid + 1;
            } else {
                end = mid;
            }
        }
        return start;
    }
}
