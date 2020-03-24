package cn.mccreefei.technologystack.leetcode;

import java.util.Random;

/**
 * @author MccreeFei
 * @create 2020-03-24 上午10:00
 * @refer <href>https://leetcode.com/problems/shuffle-an-array/</href>
 * @idea 遍历数组索引 每一个索引随机和前面的一个索引(包含自身)进行调换
 */
public class ShuffleAnArray {
    private int[] nums;
    private Random random;
    public ShuffleAnArray(int[] nums) {
        this.nums = nums;
        this.random = new Random();
    }

    /** Resets the array to its original configuration and return it. */
    public int[] reset() {
        return nums;
    }

    /** Returns a random shuffling of the array. */
    public int[] shuffle() {
        if (nums == null) {
            return nums;
        }
        int[] clone = nums.clone();
        for (int i = 1; i < clone.length; i++) {
            int j = random.nextInt(i + 1);
            swap(clone, i, j);
        }
        return clone;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
