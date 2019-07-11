package cn.mccreefei.technologystack.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author MccreeFei
 * @create 2019-07-10 下午2:17
 * @refer <href>https://leetcode.com/problems/3sum/</href>
 * @idea 先排序 依次遍历a[i] 从后续找到两个数之和等于-a[i],考虑重复剔除 O(n^2)
 */
public class ThreeSum {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums == null || nums.length < 3) {
            return result;
        }
        Arrays.sort(nums);
        for (int i = 0; i < nums.length-2; i++) {
            //使得第一个数不会重复
            if (i > 0  && nums[i] == nums[i-1]) {
                continue;
            }
            int start = i + 1;
            int end = nums.length - 1;
            while (end > start) {
                //如果第一个数都>-nums[i],相加另一个数一定不会等于-nums[i],直接break
                if (nums[start] > -nums[i]) {
                    break;
                }
                if (nums[start] + nums[end] == -nums[i]) {
                    result.add(Arrays.asList(nums[i], nums[start], nums[end]));
                    //过滤重复的数
                    while (start < end && nums[start] == nums[start+1]) {
                        start++;
                    }
                    while (end > start && nums[end] == nums[end-1]) {
                        end--;
                    }
                    start++;
                    end--;
                } else if (nums[start] + nums[end] < -nums[i]) {
                    start++;
                } else {
                    end--;
                }
            }
        }
        return result;
    }

}
