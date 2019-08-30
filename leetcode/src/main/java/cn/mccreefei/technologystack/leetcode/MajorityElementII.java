package cn.mccreefei.technologystack.leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MccreeFei
 * @create 2019-08-30 下午3:35
 * @refer <href>https://leetcode.com/problems/majority-element-ii/</href>
 * @idea 投票法 大于1/3数量的数最多只有两个 设置两个数投票 最终判断这两个数是否大于n/3
 */
public class MajorityElementII {
    public List<Integer> majorityElement(int[] nums) {
        List<Integer> result = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return result;
        }
        int num1 = nums[0];
        int num2 = nums[0];
        int count1 = 0;
        int count2 = 0;

        for (int i = 0; i < nums.length; i++) {
            if (num1 == nums[i]) {
                count1++;
            } else if (num2 == nums[i]) {
                count2++;
            } else if (count1 == 0) {
                num1 = nums[i];
                count1 = 1;
            } else if (count2 == 0) {
                num2 = nums[i];
                count2++;
            } else {
                count1--;
                count2--;
            }
        }
        count1 = 0;
        count2 = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == num1) {
                count1++;
            } else if (nums[i] == num2) {
                count2++;
            }
        }
        if (count1 > nums.length / 3) {
            result.add(num1);
        }
        if (count2 > nums.length / 3 && num1 != num2) {
            result.add(num2);
        }
        return result;
    }

}
