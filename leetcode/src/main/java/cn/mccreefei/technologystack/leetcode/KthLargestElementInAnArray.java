package cn.mccreefei.technologystack.leetcode;

import java.util.PriorityQueue;
import java.util.Random;

/**
 * @author MccreeFei
 * @create 2019-08-27 下午7:24
 * @idea 1.最小堆 保证最小堆的个数为k 最终堆顶就是第k大的值
 *       2.快排partition思想 随机选取index 将大于nums[index]数移到右边，最终返回partitionIndex与k进行比较
 *       partitionIndex == nums.length-k 即找到该数. partitionIndex < nums.length - k 则在partitionIndex右侧再次进行
 *       partition.否则在左侧寻找
 */
public class KthLargestElementInAnArray {
    private static Random random = new Random();
    public int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        for (int num : nums) {
            priorityQueue.offer(num);
            if (priorityQueue.size() > k) {
                priorityQueue.poll();
            }
        }
        return priorityQueue.poll();
    }

    //使用快排partition思想
    public int findKthLargestSolution2(int[] nums, int k) {
        return select(nums, 0, nums.length - 1, k);
    }

    private int select(int[] nums, int start, int end, int k) {
        if (start == end) {
            return nums[start];
        }
        int randomIndex = start + random.nextInt(end - start);
        int partition = partition(nums, start, end, randomIndex);
        if (partition == nums.length - k) {
            return nums[partition];
        } else if (partition < nums.length - k) {
            return select(nums, partition+1, end, k);
        } else {
            return select(nums, start, partition-1, k);
        }

    }

    private int partition(int[] nums, int start, int end, int index) {
        int value = nums[index];
        swap(nums, index, start);
        int sortIndex = end;
        for (int i = end; i > start; i--) {
            if (nums[i] > value) {
                swap(nums, i, sortIndex);
                sortIndex--;
            }
        }
        swap(nums, sortIndex, start);
        return sortIndex;
    }

    private void swap(int[] nums, int a, int b) {
        int temp = nums[a];
        nums[a] = nums[b];
        nums[b] = temp;
    }

    public static void main(String[] args) {
        KthLargestElementInAnArray k = new KthLargestElementInAnArray();
        int[] nums = {3,2,1,5,6,4};
        int result = k.findKthLargestSolution2(nums, 2);
        System.out.println(result);
    }
}
