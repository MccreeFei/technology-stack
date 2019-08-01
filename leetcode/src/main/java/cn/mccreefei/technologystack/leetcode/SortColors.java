package cn.mccreefei.technologystack.leetcode;

import java.util.Arrays;

/**
 * @author MccreeFei
 * @create 2019-08-01 上午10:56
 * @refer <href>https://leetcode.com/problems/sort-colors/</href>
 * @idea one-pass zeroIndex记录0位置 twoIndex记录2位置
 */
public class SortColors {
    public void sortColors(int[] nums) {
        int zeroIndex = 0;
        int twoIndex = nums.length - 1;
        int current = 0;
        while (current <= twoIndex){
            if (nums[current] == 0) {
                nums[current] = nums[zeroIndex];
                nums[zeroIndex] = 0;
                zeroIndex++;
                current++;
            } else if (nums[current] == 2) {
                nums[current] = nums[twoIndex];
                nums[twoIndex] = 2;
                twoIndex--;
            } else {
                current++;
            }
        }
    }

}
